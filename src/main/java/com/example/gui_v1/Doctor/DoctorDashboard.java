package com.example.gui_v1.Doctor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static databaseConfig.DatabaseConfig.*;
import base.*;

public class DoctorDashboard {
    Doctor doctor = new Doctor(activeId);
    @FXML
    private Label activeUserName;
    @FXML
    private Label nextName;
    @FXML
    private Label nextDate;
    @FXML
    private Label recentName;
    @FXML
    private Label recentDate;
    @FXML
    private Label pendingField;
    @FXML
    private Label totalAppField;
    @FXML
    private ImageView nextImage;
    @FXML
    private Label noUpcoming;

    @FXML
    public void initialize() {
        updateLabels();
    }

    private void updateLabels() {
        activeUserName.setText(doctor.getName());

        if(doctor.getNextAppointmentPatientName() == null){
            noUpcoming.setText("No upcoming");
            nextName.setText("");
            nextDate.setText("");
            nextImage.setImage(null);
        }else {
            nextName.setText(doctor.getNextAppointmentPatientName());
            nextDate.setText(doctor.getNextAppointmentDate());
        }
        recentName.setText(doctor.getMostRecentAppointmentPatientName());
        recentDate.setText(doctor.getMostRecentAppointmentDate());
        pendingField.setText(String.valueOf(doctor.getPendingAppointments()));
        totalAppField.setText(String.valueOf(doctor.getTotalAppointments()));
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
    public void appointmentsButton(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Doctor/Appointments.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("Appointments");
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
    public void NewPrescriptionButton(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Doctor/NewPrescription.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("New Prescription");
        stage.setScene(scene);
        stage.setMaxWidth(1000);
        stage.setMaxHeight(500);
        stage.show();
    }



}