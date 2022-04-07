package com.example.w22comp1008w12prep;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class will simulate getting information from a database
 */
public class DBUtility {
    private static String user = "student";
    private static String password = "student";
    private static String connString = "jdbc:mysql://localhost:3306/edmuse";

    public static ArrayList<String> getAvailableCourseCodes()
    {
        ArrayList<String> courseCodes = new ArrayList<>();

        //connect to the DB
        try(
                Connection conn = DriverManager.getConnection(connString, user, password);
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM approvedCourses");
                )
        {
            //get a list of courses, add them to the arrayList and return them
            while (resultSet.next())
            {
                courseCodes.add(resultSet.getString("courseCode"));
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return courseCodes;
    }

    public static ArrayList<Student> getStudentsFromDB() throws SQLException {
        ArrayList<Student> students = new ArrayList<>();
//        students.add(new Student("Rose","Ruffner","3846 St. Paul StreetSt Catharines ON L2S 3A1", LocalDate.of(1975,8,27)));
//        students.add(new Student("Jack","Bradbury","867 rue des Églises Est Ste Cecile De Masham QC J0X 2W0", LocalDate.of(1979,10,14)));
//        students.add(new Student("Elanore","Sanders","1145 47th Avenue Grassland AB T0A 1V0", LocalDate.of(1940,9,25)));
//        students.add(new Student("Nancy","Walsh","1459 Harvest Moon Dr Unionville ON L3R 0L7", LocalDate.of(1999,1,12)));
//        students.add(new Student("Greta","Tolbert","642 Front Street Toronto ON M5J 2N1", LocalDate.of(1957,12,18)));
//        students.add();

        //create objects to access and read from the DB
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;
        ResultSet gradesResultSet = null;

        try{
            //1.  connect to the DB
            conn = DriverManager.getConnection(connString, user, password);

            //2.  create new Student("Barbara","Gable","3671 Scotchmere Dr Sarnia ON N7T 7T9", LocalDate.of(2002,11,13))a statement object
            statement = conn.createStatement();

            //3.  create / execute the query
            resultSet = statement.executeQuery("SELECT * FROM students");

            //4.  loop over the results
            while (resultSet.next())
            {
                Student newStudent = new Student(resultSet.getString("firstName"),
                                                 resultSet.getString("lastName"),
                                                 resultSet.getString("address"),
                                                 resultSet.getDate("birthday").toLocalDate());
                students.add(newStudent);
            }
        } catch (SQLException e)
        {
            System.out.println("Database access issue: " + e.getMessage());
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn != null)
                conn.close();
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
            if (gradesResultSet != null)
                gradesResultSet.close();
        }
        return students;
    }

    public static ArrayList<Professor> getProfessorsFromDB() throws SQLException {
        ArrayList<Professor> professors = new ArrayList<>();

        //create objects to access and read from the DB
        Connection conn = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            //1.  connect to the DB
            conn = DriverManager.getConnection(connString, user, password);

            //2.  create a new statement object
            statement = conn.createStatement();

            //3.  create / execute the query
            resultSet = statement.executeQuery("SELECT * FROM professors");

            //4.  loop over the results
            while (resultSet.next())
            {
                Professor newProfessor = new Professor(resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getString("address"),
                        resultSet.getDate("birthday").toLocalDate());
                professors.add(newProfessor);
            }
        } catch (SQLException e)
        {
            System.out.println("Database access issue: " + e.getMessage());
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        finally {
            if (conn != null)
                conn.close();
            if (statement != null)
                statement.close();
            if (resultSet != null)
                resultSet.close();
        }
        return professors;
    }

    public static ArrayList<Course> getCoursesFromDB() throws SQLException {
        ArrayList<Professor> professors = getProfessorsFromDB();
        ArrayList<Student> students = getStudentsFromDB();
        Course course1 = new Course("COMP 1008","21109","Intro to Objects",professors.get(0));
        course1.addStudent(students.get(0));
        course1.addStudent(students.get(1));
        course1.addStudent(students.get(2));

        Course course2 = new Course("COMP 1011","21110","Advanced Java",professors.get(1));
        course2.addStudent(students.get(2));
        course2.addStudent(students.get(3));
        course2.addStudent(students.get(4));
        course2.addStudent(students.get(5));

        Course course3 = new Course("COMP 1030","21111","Programming Fundamentals",professors.get(1));
        course2.addStudent(students.get(2));
        course2.addStudent(students.get(3));
        course2.addStudent(students.get(4));
        course2.addStudent(students.get(5));

        ArrayList<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        return courses;
    }

    public static int insertNewStudent(Student student) throws SQLException {
        int studentNum = -1;
        String sql = "INSERT INTO students (firstName, lastName, address, birthday) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try (
                Connection conn = DriverManager.getConnection(connString, user, password);
                )
        {
            preparedStatement = conn.prepareStatement(sql, new String[]{"studentNum"});

            //bind the values
            preparedStatement.setString(1, student.getFirstName());
            preparedStatement.setString(2, student.getLastName());
            preparedStatement.setString(3, student.getAddress());
            preparedStatement.setDate(4, Date.valueOf(student.getBirthday()));

            //execute the insert statement
            preparedStatement.executeUpdate();

            //loop over the resultset and get the student number
            rs = preparedStatement.getGeneratedKeys();
            while (rs.next())
                studentNum = rs.getInt(1);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally {
            if (rs != null)
                rs.close();
            if(preparedStatement != null)
                preparedStatement.close();
        }
        return studentNum;
    }

    public static int insertNewProfessor(Professor professor) throws SQLException {
        int professorID = -1;
        String sql = "INSERT INTO professors (firstName, lastName, address, birthday) VALUES (?,?,?,?)";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try (
                Connection conn = DriverManager.getConnection(connString, user, password);
        )
        {
            preparedStatement = conn.prepareStatement(sql, new String[]{"professorID"});

            //bind the values
            preparedStatement.setString(1, professor.getFirstName());
            preparedStatement.setString(2, professor.getLastName());
            preparedStatement.setString(3, professor.getAddress());
            preparedStatement.setDate(4, Date.valueOf(professor.getBirthday()));

            //execute the insert statement
            preparedStatement.executeUpdate();

            //loop over the resultset and get the student number
            rs = preparedStatement.getGeneratedKeys();
            while (rs.next())
                professorID = rs.getInt(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (rs != null)
                rs.close();
            if(preparedStatement != null)
                preparedStatement.close();
        }
        return professorID;
    }

    public static int insertNewCourse(Course course) throws SQLException {
        int crn = -1;
        String sql = "INSERT INTO courses (courseCode,professorID) VALUES (?,?)";
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try (
                Connection conn = DriverManager.getConnection(connString, user, password);
        )
        {
            preparedStatement = conn.prepareStatement(sql, new String[]{"crn"});

            //bind the values
            preparedStatement.setString(1, course.getCourseCode());
            preparedStatement.setInt(2, course.getProf().getProfessorID());

            //execute the insert statement
            preparedStatement.executeUpdate();

            //loop over the resultset and get the student number
            rs = preparedStatement.getGeneratedKeys();
            while (rs.next())
                crn = rs.getInt(1);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (rs != null)
                rs.close();
            if(preparedStatement != null)
                preparedStatement.close();
        }
        return crn;
    }

    public static void addTeachable(Professor professor, String courseCode)
    {
        try(
                Connection conn = DriverManager.getConnection(connString,user,password);
                PreparedStatement statement = conn.prepareStatement("INSERT INTO approvedToTeach (professorID, courseCode) VALUES (?,?)");
                )
        {
            //bind the values
            statement.setInt(1, professor.getProfessorID());
            statement.setString(2, courseCode);

            statement.executeUpdate();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method will return the name of a course, given the course code
     */
    public static String getCourseName(String courseCode)
    {
        String courseName = "";
        ResultSet resultSet = null;

        try(
                Connection conn = DriverManager.getConnection(connString, user, password);
                PreparedStatement statement = conn.prepareStatement("SELECT * FROM approvedCourses WHERE courseCode = ?");
                )
        {
            statement.setString(1, courseCode);
            resultSet = statement.executeQuery();

            //loop over the result set (even though there is only 1 record returned)
            while (resultSet.next())
                courseName=resultSet.getString("courseName");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return courseName;
    }

    public static ArrayList<Professor> professorCanTeach(String courseCode)
    {
        ArrayList<Professor> professors = new ArrayList<>();
        ResultSet resultSet = null;

        String sql = "SELECT * FROM professors INNER JOIN approvedToTeach ON professors.professorID = approvedToTeach.professorID " +
                        "WHERE courseCode = ?";

        try(
                Connection conn = DriverManager.getConnection(connString, user, password);
                PreparedStatement statement = conn.prepareStatement(sql);
        )
        {
            statement.setString(1, courseCode);
            resultSet = statement.executeQuery();

            //loop over the result set (even though there is only 1 record returned)
            while (resultSet.next())
                professors.add(new Professor(resultSet.getString("firstName"),
                                            resultSet.getString("lastName"),
                                            resultSet.getString("address"),
                                            resultSet.getDate("birthday").toLocalDate(),
                                            resultSet.getInt("professorID")));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return professors;
    }

    /**
     * This returns a list of majors (unfiltered)
     * @return
     */
    public static ArrayList<String> getMajors()
    {
        ArrayList<String> majors = new ArrayList<>();
        majors.addAll(Arrays.asList("Acupuncture","Advanced Care Paramedic","Architectural Technology",
                "Art and Design Fundamentals","Artificial Intelligence", "Aviation Management","Computer Science"));
        return majors;
    }
}
