package com.example.gui_v1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/gui_v1/Doctor/DoctorDashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaxWidth(1000);
        stage.setMaxHeight(500);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}