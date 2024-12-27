package com.example.gui_v1.Doctor;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.*;


import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static databaseConfig.DatabaseConfig.*;
import base.*;
public class Prescription {
    Doctor doctor = new Doctor(activeId);
    @FXML
    private Label activeUserName;
    @FXML HBox prescriptionHbox;
    @FXML private TextField searchBar;
    public void initialize() {
        updateLabels();
        getPrescriptions();
    }

    private void updateLabels() {
        activeUserName.setText(doctor.getName());
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
    public void getPrescriptions(){
        prescriptionHbox.getChildren().clear();
        for(Patient patient: doctor.getPatients()){
            for(String S: patient.getMedicalRecord().getPrescriptions()){
                Label name = new Label(patient.getName());
                name.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #333333;");
                Label prescription = new Label(S);
                prescription.setStyle("-fx-font-size: 13; -fx-text-fill: #333333;");
                prescription.wrapTextProperty().setValue(true);

                prescriptionHbox.setAlignment(Pos.CENTER_LEFT);
                prescriptionHbox.setSpacing(20);

                VBox card = new VBox();
                card.setSpacing(10);
                card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-padding: 20; -fx-pref-width: 200; -fx-alignment: center;");

                Image img = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource("/img/patient.png")).toExternalForm());
                ImageView imgView = new ImageView(img);
                imgView.setFitHeight(60);
                imgView.setFitWidth(60);
                imgView.setPreserveRatio(true);

                card.getChildren().addAll(imgView, name, prescription);
                prescriptionHbox.getChildren().addAll(card);


            }
        }

    }

    public void NewPrescriptionButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Doctor/NewPrescription.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle("New Prescription");
        stage.setScene(scene);
        stage.setMaxWidth(1000);
        stage.setMaxHeight(500);
        stage.show();
    }
    @FXML
    public void searchButtonClick(ActionEvent event) throws IOException {
        String search = searchBar.getText();
        prescriptionHbox.getChildren().clear();
        for(Patient patient: doctor.getPatients()){
            if(patient.getName().contains(search)){
                for(String S: patient.getMedicalRecord().getPrescriptions()){
                    Label name = new Label(patient.getName());
                    name.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #333333;");
                    Label prescription = new Label(S);
                    prescription.setStyle("-fx-font-size: 13; -fx-text-fill: #333333;");
                    prescription.wrapTextProperty().setValue(true);

                    prescriptionHbox.setAlignment(Pos.CENTER_LEFT);
                    prescriptionHbox.setSpacing(20);

                    VBox card = new VBox();
                    card.setSpacing(10);
                    card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-padding: 20; -fx-pref-width: 200; -fx-alignment: center;");

                    Image img = new javafx.scene.image.Image(Objects.requireNonNull(getClass().getResource("/img/patient.png")).toExternalForm());
                    ImageView imgView = new ImageView(img);
                    imgView.setFitHeight(60);
                    imgView.setFitWidth(60);
                    imgView.setPreserveRatio(true);

                    card.getChildren().addAll(imgView, name, prescription);
                    prescriptionHbox.getChildren().addAll(card);
                }
            }
        }
    }

}
