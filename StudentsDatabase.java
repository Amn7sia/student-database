package com.example.assuignment4;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class StudentsDatabase implements StudentsDatabaseInterface, TableInterface {
    private static Connection connection;


    StudentsDatabase() {
        connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/2nddemo", "root", "ipod2016");

            if (connection != null) {
                System.out.println("Successful connection to the Database");
            }
        } catch (SQLException error) {
            System.out.println("Exception: " + error.getMessage());
            error.printStackTrace();}
    }

    class Schedule
    {
        String ddlCreateTable, ddlPopulateTable;
        String ddlUpDateCourseInstructor, ddlUpDateInstructor;
        String Filename, Tablename;

        Schedule(String ddlCreateTable, String Filename, String Tablename) throws SQLException
        {
            this.ddlCreateTable = ddlCreateTable;
            this.Filename = Filename;
            this.Tablename = Tablename;
            this.ddlPopulateTable = TableInterface.loadDataInFileTable(Filename, Tablename);

            TableInterface.DropTable(connection, Tablename);
            TableInterface.CreateTable(connection, ddlCreateTable);
            System.out.println("\nSchedule Table has been created successfully");

            TableInterface.setLocalInFileLoading(connection);
            TableInterface.PopulateTable(connection, ddlPopulateTable);
        }
    }

    //edit this
    class Courses
    {
        String ddlCreateTable, ddlInsertFromSelect;
        String nameToTable, nameFromTable, nameofTable;

        Courses(String ddlCreateTable, String nameofTable, String nameToTable, String nameFromTable) throws SQLException
        {
            this.ddlCreateTable = ddlCreateTable;
            this.nameofTable = nameofTable;
            this.nameToTable = nameToTable;
            this.nameFromTable = nameFromTable;
            this.ddlInsertFromSelect = TableInterface.fromSelectcfs(nameToTable, nameFromTable);

            TableInterface.DropTable(connection, nameofTable);
            TableInterface.CreateTable(connection, ddlCreateTable);
            System.out.println("\nCourses Table has been created successfully");

            TableInterface.InsertFromSelect(connection, ddlInsertFromSelect);
        }
    }

    class Students
    {
        String ddlCreateTable, ddlPopulateTable;
        String nameTable;

        Students(String ddlCreateTable, String nameTable) throws SQLException
        {
            this.ddlCreateTable = ddlCreateTable;
            this.nameTable = nameTable;
            this.ddlPopulateTable = ddlInsertTableStudents;

            TableInterface.DropTable(connection, nameTable);
            TableInterface.CreateTable(connection, ddlCreateTable);
            System.out.println("\nStudents Table has been created successfully");

            TableInterface.PopulateTable(connection, ddlPopulateTable);
        }

        public static void InsertRecord(Integer EMPLID, String FirstName, String LastName, String Email, Character Gender) throws SQLException {
            String ddlInsertRecord =  "INSERT INTO Students (EMPLID, FirstName, LastName, Email, Gender) VALUES ("
                    + EMPLID + ", '" + FirstName + "', '" + LastName + "', '" + Email + "', '" + Gender + "')";

            TableInterface.InsertRecord(connection, ddlInsertRecord);

        }
    }

    class Classes
    {
        String ddlCreateTable, ddlPopulateTable;
        String TableNamec;

        Classes(String ddlCreateTable, String TableNamec) throws SQLException
        {
            this.ddlCreateTable = ddlCreateTable;
            this.TableNamec = TableNamec;
            this.ddlPopulateTable = ddlInsertTableClasses;

            TableInterface.DropTable(connection, TableNamec);
            TableInterface.CreateTable(connection, ddlCreateTable);
            System.out.println("\nClasses Table has been created successfully");

            TableInterface.PopulateTable(connection, ddlInsertTableClasses);
        }

        public static void InsertRecord(String CourseID, Integer StudentID, Integer SectionNumber, Integer Year, String Semester, Character Grade) throws SQLException {
            String ddlInsertRecord = "INSERT INTO Classes (CourseID, StudentID, SectionNumber, Year, Semester, Grade) VALUES ('"
                    + CourseID + "', " + StudentID + ", " + SectionNumber + ", " + Year + ", '" + Semester + "', '" + Grade +"')";
            TableInterface.InsertRecord(connection, ddlInsertRecord);
        }

        public static void UpdateGrade(Integer StudentID, Character NewGrade) throws SQLException {
            String ddlUpdateField = StudentsDatabaseInterface.ddlUpdateGrade(StudentID, NewGrade);
            TableInterface.UpdateField(connection, ddlUpdateField);
        }

    }

    class AggregateGrades
    {
        String NametoTable, NamefromTable;
        String ddlCreateTable, ddlPopulateTable;
        ResultSet RS;

        AggregateGrades(String NametoTable, String NamefromTable) throws SQLException
        {
            this.NametoTable = NametoTable;
            this.NamefromTable = NamefromTable;
            this.ddlCreateTable = StudentsDatabaseInterface.ddlCreateTableAggregateGrades;
            this.ddlPopulateTable = StudentsDatabaseInterface.ddlInsertTableAggregateGrades(NametoTable, NamefromTable);

            TableInterface.DropTable(connection, NametoTable);
            TableInterface.CreateTable(connection, ddlCreateTable);

            TableInterface.InsertFromSelect(connection, ddlPopulateTable);
            System.out.println("\nAggregateGrades Table populated successfully");
            RS = TableInterface.getTable(connection, NametoTable);
            System.out.println("\nQuery on AggregateGrades executed successfully");

        }

        public static Map<Character, Integer> getAggregateGrades(String TABLENAME)
        {
            Map<Character, Integer> mapAggregateGrades = new HashMap<>();

            try {
                ResultSet RS = TableInterface.getTable(connection, TABLENAME);

                while (RS.next()) {
                    mapAggregateGrades.put(RS.getString("Grade").charAt(0), RS.getInt("StudentAmount"));
                }
            }
            catch(SQLException e){System.out.println(e);}

            return mapAggregateGrades;
        }

        public static Map<Character, Integer> UpdateAggregateGrades(String TABLENAME) throws SQLException {
            Map<Character, Integer> mapAggregateGrades1 = new HashMap<>();

            TableInterface.DropTable(connection, "AggregateGrades");
            String ddlCreateTable = StudentsDatabaseInterface.ddlCreateTableAggregateGrades;
            TableInterface.CreateTable(connection, ddlCreateTable);
            String ddlPopulateTable = StudentsDatabaseInterface.ddlInsertTableAggregateGrades("AggregateGrades", "Classes");
            TableInterface.InsertFromSelect(connection, ddlPopulateTable);

            try {
                ResultSet RS = TableInterface.getTable(connection, "AggregateGrades");

                while (RS.next()) {
                    mapAggregateGrades1.put(RS.getString("Grade").charAt(0), RS.getInt("StudentAmount"));
                }
            }
            catch(SQLException e){System.out.println(e);}

            return mapAggregateGrades1;
        }

    }

    public static class Student {
        private String FirstName = null;
        private String LastName = null;
        private Character Gender = null;
        private Integer EMPLID = null;
        private String Email = null;
        private Character Grade = null;

        public Student(Integer EMPLID, String FirstName, String LastName, String Email, Character Gender, Character Grade) {
            this.FirstName = FirstName;
            this.LastName = LastName;
            this.Gender = Gender;
            this.EMPLID = EMPLID;
            this.Email = Email;
            this.Grade = Grade;
        }

        public String getFirstName() {return FirstName;}
        public String getLastName() {return LastName;}
        public Character getGender() {return Gender;}
        public Integer getEMPLID() {return EMPLID;}
        public String getEmail() {return Email;}
        public Character getGrade() {return Grade;}
    }

    public static TableView<Student> table() {
        TableView<Student> tableView = new TableView<Student>();

        TableColumn<Student, String> column1 = new TableColumn<>("EMPLID");
        TableColumn<Student, String> column2 = new TableColumn<>("FirstName");
        TableColumn<Student, String> column3 = new TableColumn<>("LastName");
        TableColumn<Student, String> column4 = new TableColumn<>("Email");
        TableColumn<Student, String> column5 = new TableColumn<>("Gender");
        TableColumn<Student, String> column6 = new TableColumn<>("Grade");

        column1.setCellValueFactory(new PropertyValueFactory<>("EMPLID"));
        column2.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        column3.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        column4.setCellValueFactory(new PropertyValueFactory<>("Email"));
        column5.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        column6.setCellValueFactory(new PropertyValueFactory<>("Grade"));

        tableView.getColumns().add(column1);
        tableView.getColumns().add(column2);
        tableView.getColumns().add(column3);
        tableView.getColumns().add(column4);
        tableView.getColumns().add(column5);
        tableView.getColumns().add(column6);

        try {
            PreparedStatement cmd = connection.prepareStatement("SELECT * FROM Students");
            ResultSet rs = cmd.executeQuery();

            while (rs.next()) {
                int EMPLID = rs.getInt("EMPLID");
                String FirstName = rs.getString("FirstName");
                String LastName = rs.getString("LastName");
                String Email = rs.getString("Email");
                char Gender = rs.getString("Gender").charAt(0);


                PreparedStatement cmd2 = connection.prepareStatement("SELECT Grade FROM Classes WHERE StudentID = " + Integer.toString(EMPLID));
                ResultSet rs2 = cmd2.executeQuery();
                rs2.next();

                char Grade = rs2.getString("Grade").charAt(0);

                tableView.getItems().add(new Student(EMPLID, FirstName, LastName, Email, Gender, Grade));
            }
        } catch (SQLException x) {
            System.out.println("Exception: " + x.getMessage());
            x.printStackTrace();
        }

        tableView.setPrefSize(500, 1500);

        return tableView;
    }
}
