package com.example.gui_v1.Patient;
import base.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    @FXML private HBox Appointmentsbox;
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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/MyPrescriptions.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
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
        Appointmentsbox.getChildren().clear();
        for (Doctor doctor : doctors){
            GridPane gridPane = new GridPane();
            gridPane.getStyleClass().add("doctor-grid");
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            Label name = new Label(doctor.getName());
            Label specialization = new Label(doctor.getSpecialty());
            Button bookButton = new Button("Book Appointment");
            bookButton.setOnAction(e -> {
                try {
                    hospital.scheduleAppointment(patient, doctor, datePicker.getValue());
                    LoadDoctors();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            });

            gridPane.add(name, 0, 0);
            gridPane.add(specialization, 1, 0);
            gridPane.add(bookButton, 2, 0);

            Appointmentsbox.getChildren().add(gridPane);
        }
    }

    public void handleSearchButton(ActionEvent event) throws IOException {
        String search = searchBar.getText();

        if (search == null || search.isEmpty()) {
            LoadDoctors();
        } else {
            Hospital hospital = Hospital.getInstance();
            ArrayList<Doctor> doctors = hospital.getDoctors();
            Appointmentsbox.getChildren().clear();

            for (Doctor doctor : doctors) {
                if (doctor.getName().toLowerCase().contains(search.toLowerCase())) {
                    GridPane gridPane = new GridPane();
                    gridPane.getStyleClass().add("doctor-grid");
                    gridPane.setHgap(10);
                    gridPane.setVgap(10);

                    Label name = new Label(doctor.getName());
                    Label specialization = new Label(doctor.getSpecialty());
                    Button bookButton = new Button("Book Appointment");
                    bookButton.setOnAction(e -> {
                        try {
                            hospital.scheduleAppointment(patient, doctor, datePicker.getValue());
                            LoadDoctors();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    });

                    gridPane.add(name, 0, 0);
                    gridPane.add(specialization, 1, 0);
                    gridPane.add(bookButton, 2, 0);

                    Appointmentsbox.getChildren().add(gridPane);
                }
            }
        }
    }
}
