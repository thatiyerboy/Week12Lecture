package com.example.w22comp1008w12prep;

import java.util.ArrayList;

public class Course {
    private String courseCode, crn, courseName;
    private Professor prof;
    private ArrayList<Student> students;

    public Course(String courseCode, String crn, String courseName, Professor prof) {
        this(courseCode, courseName, prof);
        setCrn(crn);
    }

    public Course(String courseCode, String courseName, Professor prof) {
        setCourseCode(courseCode);
        setCourseName(courseName);
        setProf(prof);
        students = new ArrayList<>();
    }

    /**
     * This method will add a Student to the course list called students if there is space in the class
     * Class sizes are capped at 40
     * @return
     */
    public void addStudent(Student newStudent)
    {
        if (students.size()<40)
            students.add(newStudent);
        else
            throw new IllegalArgumentException("Course is full");
    }


    public String getCourseCode() {
        return courseCode;
    }

    /**
     * This method will validate that the course code matches the correct pattern of COMP XXXX and sets the instance variables
     * @param courseCode
     */
    public void setCourseCode(String courseCode) {
        if (courseCode.matches("COMP [0-9]{4}"))
            this.courseCode = courseCode;
        else
            throw new IllegalArgumentException("Course code must match the pattern COMP XXXX where X is a number");
    }

    public String getCrn() {
        return crn;
    }

    public int getNumOfStudents()
    {
        return students.size();
    }

    public void setCrn(String crn) {
        crn = crn.trim();
        if (crn.matches("2[0-9]{4}"))
            this.crn = crn;
        else
            throw new IllegalArgumentException("CRN must match the pattern 2XXXX where X is a number");
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        courseName = courseName.trim();
        if (courseName.length()>=2 && courseName.length()<=60)
            this.courseName = courseName;
        else
            throw new IllegalArgumentException("name must be 2-50 characters");
    }

    public Professor getProf() {
        return prof;
    }

    public void setProf(Professor prof) {
        this.prof = prof;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public String toString()
    {
        return String.format("%s-%s %s-%d students",crn,courseCode,courseName,students.size());
    }
}
