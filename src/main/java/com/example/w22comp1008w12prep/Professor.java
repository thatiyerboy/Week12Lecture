package com.example.w22comp1008w12prep;

import java.time.LocalDate;
import java.util.ArrayList;

public class Professor extends Person {
    private ArrayList<String> teachables;
    private int professorID;

    /**
     * This is the constructor for the Professor class.  Because it extends the Person class,
     * we need to pass in the firstName, lastName, address and birthday as arguments and send
     * that information to the Person class's constructor.
     *
     * The method super() is what sends information to the super class.
     */
    public Professor(String firstName, String lastName, String address, LocalDate birthday) {
        super(firstName, lastName, address, birthday);
        teachables = new ArrayList<>();
    }

    public Professor(String firstName, String lastName, String address, LocalDate birthday, int professorID) {
        super(firstName, lastName, address, birthday);
        teachables = new ArrayList<>();
        setProfessorID(professorID);
    }

    public int getProfessorID() {
        return professorID;
    }

    public void setProfessorID(int professorID) {
        if (professorID>0)
            this.professorID = professorID;
        else
            throw new IllegalArgumentException("professorID must be greater than 0");
    }

    /**
     * This method will add a course code to the Professor's list of teachable subjects
     */
    public void addTeachable(String newCourseCode)
    {
        if (DBUtility.getAvailableCourseCodes().contains(newCourseCode))
            teachables.add(newCourseCode);
        else
            throw new IllegalArgumentException(newCourseCode + " is not a valid course code");
    }

    public ArrayList<String> getTeachables() {
        return teachables;
    }

    /**
     * This method will return true if the Professor has the argument (course code) in their
     * teachable list
     */
    public boolean canTeach(String courseCode)
    {
        return teachables.contains(courseCode);
    }
}
