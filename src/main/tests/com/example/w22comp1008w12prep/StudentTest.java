package com.example.w22comp1008w12prep;

import com.example.w22comp1008w12prep.models.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("Fred", "Flintstone", "55 HappyVale Dr", LocalDate.now().minusYears(15));
        student.addGrade("21259", 99);
        student.addGrade("21359", 100);
        student.addGrade("21349", 82);
      //  student.addGrade("22259", 43);
    }


    @Test
    void getNumCoursesPassed() {
        assertEquals(3, student.getNumCoursesPassed());
    }

    @Test
    void getGradeForCourse() {
        assertEquals(99, student.getGradeForCourse("21259"));
    }

    @Test
    void getAvgGrade() {
        assertEquals(94, student.getAvgGrade(), 0.5); //0.5 is sort of rounding it up or down
    }

    @Test
    void addGradeInvalidCRN(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            student.addGrade("T1234", 99);
        });
    }

    @Test
    void addGradeInvalidGradeLow(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            student.addGrade("21234", -1);
        });
    }

    @Test
    void addGradeInvalidGradeHigh(){
        Assertions.assertThrows(IllegalArgumentException.class, () ->{
            student.addGrade("21234", 199);
        });
    }

    @Test
    void getGradeForCourseInvalid(){
        assertEquals(-1, student.getGradeForCourse("21111"));
    }

}