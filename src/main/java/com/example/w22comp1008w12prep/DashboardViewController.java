package com.example.w22comp1008w12prep;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DashboardViewController implements Initializable {

    @FXML
    private ImageView logoImageView;

    @FXML
    private Label studentsLabel;

    @FXML
    private ListView<Student> studentListView;

//    @FXML
//    private Label professorLabel;

//    @FXML
//    private ListView<Professor> professorsListView;

    @FXML
    private Label coursesLabel;

    @FXML
    private ListView<Course> coursesListView;


    @FXML
    private GridPane studentGridPane;

    @FXML
    private Label nameLabel;

    @FXML
    private Label avgGradeLabel;

    @FXML
    private Label completedLabel;

    private Student studentSelected;

    @FXML
    private TableView<Professor> profTableView;

    @FXML
    private TableColumn<Professor, String> firstNameColumn;

    @FXML
    private TableColumn<Professor, String> lastNameColumn;

    @FXML
    private TableColumn<Professor, String> addressColumn;

    @FXML
    private TableColumn<Professor, Integer> ageColumn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //configure the student list to only allow 1 Student to be selected at a time
        studentListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //configure the table columns
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

        //load the data into the table
        try {
            profTableView.getItems().addAll(DBUtility.getProfessorsFromDB());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            studentListView.getItems().addAll(DBUtility.getStudentsFromDB());
            studentsLabel.setText(String.format("Students: %d", studentListView.getItems().size()));

//            professorsListView.getItems().addAll(DBUtility.getProfessorsFromDB());
//            professorLabel.setText(String.format("Professors: %d",professorsListView.getItems().size()));

            coursesListView.getItems().addAll(DBUtility.getCoursesFromDB());
            coursesLabel.setText(String.format("Courses: %d", coursesListView.getItems().size()));
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        //unless a student is selected, do no show the gridpane with student info.
        studentGridPane.setVisible(false);
    }

    @FXML
    private void createNewStudent(ActionEvent event) throws IOException {
        SceneChanger.changeScenes(event, "create-student-view.fxml","EdMuse-Create Student" );
    }

    @FXML
    private void createProfessorButton(ActionEvent event) throws IOException{
        SceneChanger.changeScenes(event, "create-professor-view.fxml","EdMuse-Create Professor" );
    }

    @FXML
    private void createCourseButton(ActionEvent event) throws IOException{
        SceneChanger.changeScenes(event, "create-course-view.fxml","EdMuse-Create Course" );
    }

    /**
     * When a student is selected from the listView, show their information on the dashboard
     */
    @FXML
    private void studentSelected()
    {
        studentSelected = studentListView.getSelectionModel().getSelectedItem();

        if (studentSelected != null)
        {
            studentGridPane.setVisible(true);
            nameLabel.setText(studentSelected.getFirstName() + " " + studentSelected.getLastName());
            avgGradeLabel.setText(String.format("%.1f%%",studentSelected.getAvgGrade()));
            completedLabel.setText(Integer.toString(studentSelected.getNumCoursesPassed()));
        }
    }
}
