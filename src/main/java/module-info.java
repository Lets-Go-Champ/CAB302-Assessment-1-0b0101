/**
 * The {@code com.example.cab302assessment10b0101} module is a JavaFX application designed for
 * managing book loans and related operations. This module includes functionalities for user
 * interaction, database management, and data validation.
 *
 * <p>Dependencies:</p>
 * <ul>
 *     <li>{@code javafx.controls} - Required for JavaFX UI controls.</li>
 *     <li>{@code javafx.fxml} - Required for loading FXML files for the UI layout.</li>
 *     <li>{@code java.sql} - Required for database connectivity and operations.</li>
 *     <li>{@code java.desktop} - Required for desktop functionalities, such as file handling.</li>
 *     <li>{@code java.compiler} - Required for compiling Java code at runtime.</li>
 *     <li>{@code de.jensd.fx.glyphs.fontawesome} - Required for using Font Awesome icons in the UI.</li>
 *     <li>{@code org.apache.commons.validator} - Required for validating user inputs.</li>
 *     <li>{@code commons.io} - Required for various IO utilities.</li>
 *     <li>{@code org.jsoup} - Required for parsing HTML and web scraping.</li>
 * </ul>
 *
 * <p>Package Accessibility:</p>
 * <ul>
 *     <li>Opens the {@code com.example.cab302assessment10b0101.controllers} package to
 *     {@code javafx.fxml} for FXML processing.</li>
 *     <li>Opens the {@code com.example.cab302assessment10b0101.model} package to
 *     {@code org.junit.jupiter.api} for unit testing.</li>
 * </ul>
 *
 * <p>Exported Packages:</p>
 * <ul>
 *     <li>{@code com.example.cab302assessment10b0101} - Main application package.</li>
 *     <li>{@code com.example.cab302assessment10b0101.controllers} - Contains controller classes
 *     for managing UI interactions.</li>
 *     <li>{@code com.example.cab302assessment10b0101.model} - Contains model classes that represent
 *     the data structure.</li>
 *     <li>{@code com.example.cab302assessment10b0101.Utility} - Contains utility classes for
 *     common functionalities.</li>
 * </ul>
 */
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