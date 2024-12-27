package com.example.gui_v1.Doctor;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static databaseConfig.DatabaseConfig.*;
import base.*;

public class Appointments {
    Doctor doctor = new Doctor(activeId);
    @FXML
    private Label activeUserName;
    @FXML
    private VBox upcomingVbox;
    @FXML Button searchButton;
    @FXML private TextField searchbar;
    public void initialize() {
        updateLabels();
        getUpcomingApps();
    }

    private void updateLabels() {
        activeUserName.setText(doctor.getName());
    }


    @FXML
    public void prescriptionButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Doctor/Prescription.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Prescription");
        stage.setScene(scene);
        stage.setMaxWidth(1000);
        stage.setMaxHeight(500);
        stage.show();
    }
    @FXML
    public void dashboardButton(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Doctor/DoctorDashboard.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.setMaxWidth(1000);
        stage.setMaxHeight(500);
        stage.show();
    }
    @FXML
    public void logoutButton(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/LoginScreen.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaxWidth(1000);
        stage.setMaxHeight(500);
        stage.show();
    }

    @FXML
    public void getUpcomingApps(){
        upcomingVbox.getChildren().clear();
        for (Appointment appointment : doctor.getUpcomingAppointments()) {
            Image img = new Image(Objects.requireNonNull(getClass().getResource("/img/patient.png")).toExternalForm());
            ImageView imgView = new ImageView(img);
            imgView.setFitHeight(50);
            imgView.setFitWidth(50);
            imgView.setPreserveRatio(true);

            Button cancel = new Button("Cancel");
            cancel.setStyle("-fx-background-color: #FF6B5C; -fx-text-fill: white; -fx-pref-width: 100;");
            cancel.setOnAction(e -> {
                doctor.cancelAppointment(appointment.getId());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Appointment Cancelled");
                alert.setHeaderText(null);
                alert.setContentText("The appointment has been successfully cancelled.");
                alert.showAndWait();
            });

            Label l1 = new Label(appointment.getPatient().getName());
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
    @FXML
    private void searchButtonClick(ActionEvent event) throws IOException {
        String search = searchbar.getText();
        if (search.isEmpty()) {
            getUpcomingApps();
        } else {
            upcomingVbox.getChildren().clear();
            for (Appointment appointment : doctor.getUpcomingAppointments()) {
                if (appointment.getPatient().getName().contains(search)) {
                    Image img = new Image(Objects.requireNonNull(getClass().getResource("/img/patient.png")).toExternalForm());
                    ImageView imgView = new ImageView(img);
                    imgView.setFitHeight(50);
                    imgView.setFitWidth(50);
                    imgView.setPreserveRatio(true);

                    Button cancel = new Button("Cancel");
                    cancel.setStyle("-fx-background-color: #FF6B5C; -fx-text-fill: white; -fx-pref-width: 100;");
                    cancel.setOnAction(e -> {
                        doctor.cancelAppointment(appointment.getId());
                    });

                    Label l1 = new Label(appointment.getPatient().getName());
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

}
