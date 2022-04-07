package com.example.w22comp1008w12prep;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CreateStudentViewController implements Initializable {

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private DatePicker birthdayDatePicker;

    @FXML
    private Label errMsgLabel;

    @FXML
    private ComboBox<String> majorComboBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errMsgLabel.setText("");
        majorComboBox.getItems().addAll(DBUtility.getMajors());
    }

    @FXML
    private void submitButton()
    {
        try {
            Student newStudent = new Student(firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    addressTextField.getText(),
                    birthdayDatePicker.getValue());

            //1. insert the student into the database
            int studentNum = DBUtility.insertNewStudent(newStudent);

            //2. clear the fields
            clearFields();

            //3. display the new student object
            errMsgLabel.setText("Student Num: "+studentNum + " "+ newStudent.toString());
        } catch (IllegalArgumentException exception)
        {
            errMsgLabel.setText(exception.getMessage());
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void clearFields()
    {
        firstNameTextField.clear();
        lastNameTextField.clear();
        addressTextField.clear();
        birthdayDatePicker.getEditor().clear();
    }

    @FXML
    private void changeToDashboard(ActionEvent event) throws IOException {
        SceneChanger.changeScenes(event, "dashboard-view.fxml","Edmuse");
    }

    @FXML
    private void majorSelected()
    {
        errMsgLabel.setText("Major selected: "+ majorComboBox.getValue());
    }
}