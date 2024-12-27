package com.example.gui_v1.Patient;
import base.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static databaseConfig.DatabaseConfig.*;
public class UpcomingAppointments {
    Patient patient = new Patient(activeId);

    @FXML
    private Label activeUserName;
    @FXML
    private VBox upcomingVbox;
    @FXML
    private TextField searchBar;

    public void initialize() {
        LoadUpcoming();
        updateLabels();
    }

    private void updateLabels() {
        activeUserName.setText(patient.getName());
    }

    @FXML
    public void handleViewBillsButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/MyBills.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void handleLogoutButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/LoginScreen.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void handleViewPrescriptionButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/MyPrescription.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void dashBoardButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/PatientDashboard.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void LoadUpcoming() {
        upcomingVbox.getChildren().clear();
        for (Appointment appointment : patient.getUpcomingAppointments()) {
            Image img = new Image(Objects.requireNonNull(getClass().getResource("/img/Doctor.png")).toExternalForm());
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(50);
            imgView.setFitWidth(50);
            imgView.setPreserveRatio(true);

            Button cancel = new Button("Cancel");
            cancel.setStyle("-fx-background-color: #FF6B5C; -fx-text-fill: white; -fx-pref-width: 100;");
            cancel.setOnAction(e -> {
                appointment.getDoctor().cancelAppointment(appointment.getId());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Cancelled");
                alert.setHeaderText(null);
                alert.setContentText("The appointment has been successfully cancelled.");
                alert.showAndWait();
            });

            Label l1 = new Label(appointment.getDoctor().getName());
            l1.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #333333;");
            Label l2 = new Label(appointment.getDateTime().toString());
            l2.setStyle("-fx-font-size: 14; -fx-text-fill: #555555;");

            HBox app = new HBox(imgView, l1, l2, cancel);
            app.setAlignment(Pos.CENTER_LEFT);
            app.setSpacing(20);
            app.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-padding: 15; -fx-pref-width: 100%;");

            upcomingVbox.setAlignment(Pos.CENTER);
            upcomingVbox.prefWidth(450);
            upcomingVbox.setSpacing(10);
            upcomingVbox.getChildren().addAll(app);
        }
    }

    public void searchButtonClick(ActionEvent event) throws IOException {
        String search = searchBar.getText();
        if (search.isEmpty()) {
            LoadUpcoming();
        } else {
            upcomingVbox.getChildren().clear();
            for (Appointment appointment : patient.getUpcomingAppointments()) {
                if (appointment.getDoctor().getName().contains(search)) {
                    Image img = new Image(Objects.requireNonNull(getClass().getResource("/img/patient.png")).toExternalForm());
                    ImageView imgView = new ImageView(img);
                    imgView.setFitHeight(50);
                    imgView.setFitWidth(50);
                    imgView.setPreserveRatio(true);

                    Button cancel = new Button("Cancel");
                    cancel.setStyle("-fx-background-color: #FF6B5C; -fx-text-fill: white; -fx-pref-width: 100;");
                    cancel.setOnAction(e -> {
                        appointment.getDoctor().cancelAppointment(appointment.getId());
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Appointment Cancelled");
                        alert.setHeaderText(null);
                        alert.setContentText("The appointment has been successfully cancelled.");
                        alert.showAndWait();
                    });

                    Label l1 = new Label(appointment.getDoctor().getName());
                    l1.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #333333;");
                    Label l2 = new Label(appointment.getDateTime().toString());
                    l2.setStyle("-fx-font-size: 14; -fx-text-fill: #555555;");

                    HBox app = new HBox(imgView, l1, l2, cancel);
                    app.setAlignment(Pos.CENTER_LEFT);
                    app.setSpacing(20);
                    app.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-padding: 15; -fx-pref-width: 100%;");

                    upcomingVbox.setAlignment(Pos.CENTER);
                    upcomingVbox.prefWidth(450);
                    upcomingVbox.setSpacing(10);
                    upcomingVbox.getChildren().addAll(app);
                }
            }
        }
    }
    public void pastAppointmentsButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/PastAppointments.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    public void createAppointmentButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/CreateAppointments.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
