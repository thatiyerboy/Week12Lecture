package com.example.w22comp1008w12prep;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateCourseViewController implements Initializable {

    @FXML
    private ImageView logoImageView;

    @FXML
    private TextField nameTextField;

    @FXML
    private ComboBox<String> courseCodeComboBox;

    @FXML
    private ComboBox<Professor> professorComboBox;

    @FXML
    private Spinner<Integer> numOfStudentsSpinner;

    @FXML
    private Label errMsgLabel;

    @FXML
    void changeToDashboard(ActionEvent event) throws IOException {
        SceneChanger.changeScenes(event,"dashboard-view.fxml","Edmused LMS");
    }

    @FXML
    void submitButton(ActionEvent event) throws SQLException {
        if (courseCodeComboBox.getValue()!=null && professorComboBox.getValue()!=null)
        {
            Course course = new Course(courseCodeComboBox.getValue(),
                    nameTextField.getText(),
                    professorComboBox.getValue());
            //insert the course into the DB
            int crn = DBUtility.insertNewCourse(course);
            course.setCrn(Integer.toString(crn));

            errMsgLabel.setText(course.toString());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate the course code combobox
        courseCodeComboBox.getItems().addAll(DBUtility.getAvailableCourseCodes());
    }

    @FXML
    private void courseCodeSelected()
    {
        if (courseCodeComboBox.getValue() != null)
        {
            nameTextField.setText(DBUtility.getCourseName(courseCodeComboBox.getValue()));
            professorComboBox.getItems().clear();
            professorComboBox.getItems().addAll(DBUtility.professorCanTeach(courseCodeComboBox.getValue()));
        }
    }
}
