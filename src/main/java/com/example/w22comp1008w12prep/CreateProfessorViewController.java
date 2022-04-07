package com.example.w22comp1008w12prep;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateProfessorViewController implements Initializable {

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
    private VBox coursesVBox;

    @FXML
    void changeToDashboard(ActionEvent event) throws IOException {
        SceneChanger.changeScenes(event,"dashboard-view.fxml","EdMused LMS");
    }

    @FXML
    void submitButton(ActionEvent event) {
        try {
            Professor newProf = new Professor(firstNameTextField.getText(),
                    lastNameTextField.getText(),
                    addressTextField.getText(),
                    birthdayDatePicker.getValue());

            //insert new Professor into the database
            int professorID = DBUtility.insertNewProfessor(newProf);
            newProf.setProfessorID(professorID);

            //add teachable subjects
            for (Node node : coursesVBox.getChildren())
            {
                //check if we are selected a checkbox from the VerticalBox
                if (node.getClass() == CheckBox.class)
                {
                    CheckBox checkBox = (CheckBox) node;
                    if (checkBox.isSelected())
                       DBUtility.addTeachable(newProf, checkBox.getText());
                }
            }


            errMsgLabel.setText(newProf.toString());
        } catch (Exception e)
        {
            errMsgLabel.setText(e.getMessage());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errMsgLabel.setText("");

        //Load all the course code from the DB
        ArrayList<String> courseCodes = DBUtility.getAvailableCourseCodes();

        //loop over them and create CheckBox objects with the course code
        for (String courseCode : courseCodes){
            CheckBox checkBox = new CheckBox(courseCode);
            coursesVBox.getChildren().add(checkBox);
        }
    }
}
