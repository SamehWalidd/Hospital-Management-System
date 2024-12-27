package com.example.gui_v1.Patient;
import base.*;
import com.google.protobuf.StringValue;
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

public class PatientEventHandlers {
    Patient patient = new Patient(activeId);

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

    public void initialize() {
        updateLabels();
    }

    private void updateLabels() {
        activeUserName.setText(patient.getName());

        if(patient.getNextAppointmentDoctorName() == null){
            noUpcoming.setText("No upcoming");
            nextName.setText("");
            nextDate.setText("");
            nextImage.setImage(null);
        }else {
            nextName.setText(patient.getNextAppointmentDoctorName());
            nextDate.setText(patient.getNextAppointmentDate());
        }
        recentName.setText(patient.getMostRecentAppointmentDoctorName());
        recentDate.setText(patient.getMostRecentAppointmentDate());
        pendingField.setText("$"+String.valueOf(patient.getBilling().getTotal()));
        totalAppField.setText(String.valueOf(patient.getTotalAppointments()));
    }

    @FXML
    public void handleLogoutButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/LoginScreen.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void handleViewPrescriptionButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/MyPrescriptions.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void handleViewAppointmentsButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/MyAppointments.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void handleViewBillsButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/MyBills.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

}
