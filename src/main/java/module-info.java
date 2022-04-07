module com.example.w22comp1008w12prep {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.w22comp1008w12prep to javafx.fxml;
    exports com.example.w22comp1008w12prep;
    exports com.example.w22comp1008w12prep.controllers;
    opens com.example.w22comp1008w12prep.controllers to javafx.fxml;
    exports com.example.w22comp1008w12prep.models;
    opens com.example.w22comp1008w12prep.models to javafx.fxml;
    exports com.example.w22comp1008w12prep.utilities;
    opens com.example.w22comp1008w12prep.utilities to javafx.fxml;
}