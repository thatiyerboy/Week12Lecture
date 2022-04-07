module com.example.w22comp1008w12prep {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.w22comp1008w12prep to javafx.fxml;
    exports com.example.w22comp1008w12prep;
}