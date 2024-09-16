module com.example.cab302assessment10b0101 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.compiler;

    opens com.example.cab302assessment10b0101.controllers to javafx.fxml;
    opens com.example.cab302assessment10b0101.exceptions to javafx.fxml;
    exports com.example.cab302assessment10b0101;
    exports com.example.cab302assessment10b0101.controllers;
}