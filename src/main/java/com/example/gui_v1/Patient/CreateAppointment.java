package com.example.gui_v1.Patient;
import base.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import static databaseConfig.DatabaseConfig.*;

public class CreateAppointment {
    Patient patient = new Patient(activeId);

    @FXML
    private Label activeUserName;
    @FXML private TextField searchBar;
    @FXML private GridPane AppointmentsHbox;
    @FXML private DatePicker datePicker;

    public void initialize() {
        updateLabels();
        LoadDoctors();
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

    public void UpcomingAppointmentsButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/MyAppointments.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    public void PastAppointments(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/PastAppointment.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    private void LoadDoctors() {
        Hospital hospital = Hospital.getInstance();
        ArrayList<Doctor> doctors = hospital.getDoctors();
        AppointmentsHbox.getChildren().clear();
    }

    public void handleSearchButton(ActionEvent event) throws IOException {
        String search = searchBar.getText();

        if (search == null || search.isEmpty()) {
            LoadDoctors();
        } else {
            Hospital hospital = Hospital.getInstance();
            ArrayList<Doctor> doctors = hospital.getDoctors();
            AppointmentsHbox.getChildren().clear();

        }
    }
}
