package com.example.assuignment4;

public interface StudentsDatabaseInterface {

    String ddlCreateTableSchedule =
            "CREATE TABLE Schedule(" +
                    "CourseID CHAR(12) NOT NULL UNIQUE, " +
                    "SectionNumber VARCHAR(8) NOT NULL UNIQUE, " +
                    "Title VARCHAR(64), " +
                    "Year INT, " +
                    "Semester CHAR(6), " +
                    "Instructor VARCHAR(24), " +
                    "Department CHAR(16), " +
                    "Program VARCHAR(48), " +
                    "PRIMARY KEY(CourseID, SectionNumber))";

    String ddlCreateTableStudents =
            "CREATE TABLE Students(" +
                    "EMPLID INT PRIMARY KEY, " +
                    "FirstName VARCHAR(32) NOT NULL, " +
                    "LastName VARCHAR(32) NOT NULL, " +
                    "Email VARCHAR(64) NOT NULL, " +
                    "Gender CHAR CHECK (Gender = 'F' OR Gender = 'M' OR Gender = 'U'))";

    String ddlCreateTableCourses =
            "CREATE TABLE Courses(" +
                    "CourseID CHAR(12) PRIMARY KEY REFERENCES Schedule(CourseID), " +
                    "CourseTitle VARCHAR(64) REFERENCES Schedule(Title), " +
                    "Department CHAR(16) REFERENCES Schedule(Department))";

    String ddlCreateTableClasses =
            "CREATE TABLE Classes(" +
                    "CourseID VARCHAR(12), " +
                    "StudentID INT, " +
                    "SectionNumber VARCHAR(8) NOT NULL, " +
                    "Year INT, " +
                    "Semester CHAR(6), " +
                    "Grade CHAR CHECK(Grade = 'A' OR Grade = 'B' OR Grade = 'C' OR Grade = 'D' OR Grade = 'F' OR Grade = 'W'))";

    String ddlAggregateGrades = "SELECT COUNT(StudentID), Grade FROM Classes GROUP BY Grade";

    String ddlCreateTableAggregateGrades = "CREATE TABLE AggregateGrades(Grade CHAR, StudentAmount INT)";

    String ddlInsertTableStudents =
            "INSERT INTO Students VALUES " +
                    "(216725123, 'Name-1', 'LName-1', 'name@gmail.com', 'M'), " +
                    "(276542689, 'Name-2', 'LName-2', 'name@hotmail.com', 'F'), " +
                    "(123456789, 'Name-3', 'LName-3', 'name@yahoo.com', 'U'), " +
                    "(264518978, 'Name-4', 'LName-4', 'name@facebook.com', 'M'), " +
                    "(245893155, 'Name-5', 'LName-5', 'name@outlook.com', 'F'), " +
                    "(365427985, 'Name-6', 'LName-6', 'name@microsoft.com', 'M'), " +
                    "(987654321, 'Name-7', 'LName-7', 'name@google.com', 'M'), " +
                    "(756489345, 'Name-8', 'LName-8', 'name@ccny.com', 'F'), " +
                    "(698125748, 'Name-9', 'LName-9', 'name@qc.com', 'F'), " +
                    "(645873156, 'Name-10', 'LName-10', 'name@hc.com', 'M'), " +
                    "(985435761, 'Name-11', 'LName-11', 'name@nyc.com', 'M'), " +
                    "(145697531, 'Name-12', 'LName-12', 'name@nyu.com', 'M'), " +
                    "(468239874, 'Name-13', 'LName-13', 'name@ccny.edu.com', 'F'), " +
                    "(254678965, 'Name-14', 'LName-14', 'name@email.com', 'M'), " +
                    "(365489124, 'Name-15', 'LName-15', 'name@googlemail.com', 'F'), " +
                    "(125478965, 'Name-16', 'LName-16', 'name@mail.com', 'M')";

    String ddlInsertTableClasses =
            "INSERT INTO Classes VALUES " +
                    "('22100 F', 216725123, '32131', '2021', 'Spring', 'C'), " +
                    "('22100 F', 276542689, '32132', '2021', 'Spring', 'B'), " +
                    "('22100 P', 123456789, '32150', '2021', 'Spring', 'F'), " +
                    "('22100 R', 264518978, '32131', '2021', 'Spring', 'A'), " +
                    "('22100 F', 245893155, '32150', '2021', 'Spring', 'W'), " +
                    "('22100 P', 365427985, '32131', '2021', 'Spring', 'C'), " +
                    "('22100 F', 987654321, '32150', '2021', 'Spring', 'D'), " +
                    "('22100 R', 756489345, '32150', '2021', 'Spring', 'A'), " +
                    "('22100 R', 698125748, '32150', '2021', 'Spring', 'B'), " +
                    "('22100 F', 645873156, '32132', '2021', 'Spring', 'F'), " +
                    "('22100 P', 985435761, '32132', '2021', 'Spring', 'A'), " +
                    "('22100 R', 145697531, '32131', '2021', 'Spring', 'A'), " +
                    "('22100 P', 468239874, '32150', '2021', 'Spring', 'W'), " +
                    "('22100 P', 254678965, '32132', '2021', 'Spring', 'D'), " +
                    "('22100 R', 365489124, '32131', '2021', 'Spring', 'C'), " +
                    "('22100 F', 125478965, '32131', '2021', 'Spring', 'A')";


    static String ddlUpdateCourseInstructor(String CourseID, String SectionNumber, String InstructorName)
    {
        return "UPDATE Classes" +
                " SET Instructor = " + InstructorName +
                " WHERE CourseID = " + CourseID + " AND * " + "SectionNumber = " + SectionNumber;
    }

    static String ddlUpdateInstructor(String InstructorName, String NewInstructorName)
    {
        return "UPDATE Classes" +
                " SET Instructor = " + NewInstructorName +
                " WHERE Instructor = " + InstructorName;
    }

    static String ddlUpdateGrade(Integer StudentID, Character NewGrade)
    {
        return "UPDATE Classes" +
                " SET Grade = " + "'" + NewGrade + "'" +
                " WHERE StudentID = " + StudentID;
    }

    /*static String ddlInsertTableAggregateGrades(String NameToTable, String NameFromTable)
    {
        return "INSERT INTO " + NameToTable +
                " SELECT Grade, StudentAmount FROM " + NameFromTable +
                " GROUP BY Grade";
    }*/

    static String ddlInsertTableAggregateGrades(String NameToTable, String NameFromTable)
    {
        return "INSERT INTO " + NameToTable +
                " SELECT Grade, COUNT(Grade) FROM " + NameFromTable +
                " GROUP BY Grade";
    }

    static String ddlInsertTableAg(String NameToTable, String NameFromTable)
    {
        return "INSERT INTO " + NameToTable +
                " SELECT Grade, COUNT(Grade) FROM " + NameFromTable +
                " GROUP BY Grade";
    }
}