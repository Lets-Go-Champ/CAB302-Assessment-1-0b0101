module com.example.cab302assessment10b0101 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.cab302assessment10b0101 to javafx.fxml;
    exports com.example.cab302assessment10b0101;
}