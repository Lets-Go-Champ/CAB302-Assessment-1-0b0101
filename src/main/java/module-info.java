module com.example.cab302assessment10b0101 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.compiler;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.apache.commons.validator;
    requires commons.io;
    requires org.jsoup;

    opens com.example.cab302assessment10b0101.controllers to javafx.fxml;
    opens com.example.cab302assessment10b0101.model to org.junit.jupiter.api;

    exports com.example.cab302assessment10b0101;
    exports com.example.cab302assessment10b0101.controllers;
    exports com.example.cab302assessment10b0101.model;
    exports com.example.cab302assessment10b0101.Utility;

}