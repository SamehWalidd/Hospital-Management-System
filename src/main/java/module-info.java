module com.example.gui_v1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;
    requires protobuf.java;

    opens com.example.gui_v1 to javafx.fxml;
    opens com.example.gui_v1.Doctor to javafx.fxml;
    opens com.example.gui_v1.Patient to javafx.fxml;

    exports com.example.gui_v1;
    exports com.example.gui_v1.Doctor;
    exports com.example.gui_v1.Patient;
}