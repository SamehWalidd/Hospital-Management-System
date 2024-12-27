package com.example.gui_v1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.sql.*;
import static databaseConfig.DatabaseConfig.*;
import base.*;

public class HelloController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorMessage;
    @FXML
    private TextField contactInfoField;

    @FXML
    private void NewAccount(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/SignUpScreen.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void AlreadyHaveAccButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/LoginScreen.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void loginButton(ActionEvent event) throws IOException {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        activeId = Integer.parseInt(user);

        if (user.isEmpty() || pass.isEmpty()) {
            errorMessage.setText("Please enter both user and pass.");
            return;
        }

        try {
            try (Connection connection = DriverManager.getConnection(url, username, password)) {
                String query = "SELECT role FROM login WHERE id = ? AND password = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, activeId);
                preparedStatement.setString(2, pass);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String role = resultSet.getString("role");
                    Parent root;
                    if ("Doctor".equals(role)) {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Doctor/doctorDashboard.fxml")));
                        
                    } else if ("Patient".equals(role)) {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/patientDashboard.fxml")));
                    } else if ("Admin".equals(role)) {
                        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/adminDashboard.fxml")));
                    } else {
                        errorMessage.setText("Invalid role.");
                        return;
                    }
                    Scene scene = new Scene(root, 1000, 500);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } else {
                    errorMessage.setText("Invalid username or pass.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                errorMessage.setText("Database error.");
            }
        } catch (NumberFormatException e) {
            errorMessage.setText("User ID must be an integer.");
        }
    }

    
        @FXML
    private void signupButton(ActionEvent event) throws IOException {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        String contactInfo = contactInfoField.getText();

        if (user.isEmpty() || pass.isEmpty() || contactInfo.isEmpty()) {
            errorMessage.setText("All fields are required.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            String insertLoginQuery = "INSERT INTO login (password, role) VALUES (?, ?)";
            PreparedStatement loginStatement = connection.prepareStatement(insertLoginQuery, Statement.RETURN_GENERATED_KEYS);
            loginStatement.setString(1, pass);
            loginStatement.setString(2, "Patient");
            int affectedRows = loginStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = loginStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    activeId = generatedKeys.getInt(1);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Account Created");
                    alert.setHeaderText(null);
                    alert.setContentText("Your user ID is: " + activeId);
                    alert.showAndWait();
        
                    Patient patient = new Patient(user, contactInfo);

                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/LoginScreen.fxml")));
                    Scene scene = new Scene(root, 1000, 500);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();   
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            errorMessage.setText("Database error.");
        }

    }
}