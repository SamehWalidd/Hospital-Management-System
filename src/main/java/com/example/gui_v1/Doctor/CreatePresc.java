package com.example.gui_v1.Doctor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

import static databaseConfig.DatabaseConfig.*;
import base.*;


public class CreatePresc {
    Doctor doctor = new Doctor(activeId);
    @FXML
    private Label activeUserName;
    @FXML
    private TextField patientId;
    @FXML
    private TextArea prescriptionText;
    public void initialize() {
        updateLabels();
    }

    private void updateLabels() {
        activeUserName.setText(doctor.getName());
    }
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
    public void sumbitPres(ActionEvent event) throws IOException {
        if(patientId.getText().isEmpty() || prescriptionText.getText().isEmpty() || !patientId.getText().matches("[0-9]+")){
            return;
        }

        int patId = Integer.parseInt(patientId.getText());
        String presc = prescriptionText.getText();
        Patient patient = new Patient(patId);
        patient.getMedicalRecord().addPrescription(presc);

        prescriptionButton(event);
    }

}
