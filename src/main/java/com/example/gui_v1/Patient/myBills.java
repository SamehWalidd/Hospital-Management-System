package com.example.gui_v1.Patient;
import base.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static databaseConfig.DatabaseConfig.*;

public class myBills {
    Patient patient = new Patient(activeId);

    @FXML
    private Label activeUserName;
    @FXML
    private VBox ServiceVbox;
    @FXML
    private TextField searchBar;

    public void initialize() {
        LoadBills();
        updateLabels();
    }

    private void updateLabels() {
        activeUserName.setText(patient.getName());
    }

    public void LoadBills(){
        ServiceVbox.getChildren().clear();
        for(String bill : patient.getBilling().getServices()){
            HBox Card = new HBox();
            Card.setAlignment(Pos.CENTER_LEFT);
            Card.setSpacing(10);
            Card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-padding: 15; -fx-pref-width: 100%;");

            Label service = new Label(bill);
            service.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #333333;");

            Card.getChildren().add(service);
            ServiceVbox.getChildren().add(Card);
        }
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
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/MyPrescription.fxml")));
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
    public void dashBoardButton(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/gui_v1/Patient/PatientDashboard.fxml")));
        Scene scene = new Scene(root, 1000, 500);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void searchClick(ActionEvent event) {
        ServiceVbox.getChildren().clear();
        for(String bill : patient.getBilling().getServices()){
            if(bill.toLowerCase().contains(searchBar.getText().toLowerCase())){
                HBox Card = new HBox();
                Card.setAlignment(Pos.CENTER_LEFT);
                Card.setSpacing(10);
                Card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 10; -fx-padding: 15; -fx-pref-width: 100%;");

                Label service = new Label(bill);
                service.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: #333333;");

                Card.getChildren().add(service);
                ServiceVbox.getChildren().add(Card);
            }
        }
    }

}
