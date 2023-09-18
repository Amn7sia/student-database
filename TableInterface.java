package com.example.assuignment4;

import java.sql.*;

public interface TableInterface {

    //Connection getConnection(String url, String username, String password);

    static void CreateSchema(Connection connection, String SchemaName) throws SQLException
    {
        PreparedStatement psCreateTable = connection.prepareStatement("CREATE SCHEMA " + SchemaName);
        try{psCreateTable.executeUpdate();}
        catch(SQLException e){System.out.println(e);}
    }

    static void CreateTable(Connection connection, String ddlCreateTable) throws SQLException
    {
        PreparedStatement psCreateTable = connection.prepareStatement(ddlCreateTable);
        try{psCreateTable.executeUpdate();}
        catch(SQLException e){System.out.println(e);}
    }

    static void DropTable(Connection connection, String TableName) throws SQLException
    {
        PreparedStatement psDropTable = connection.prepareStatement("DROP TABLE IF EXISTS " + TableName);
        try{psDropTable.executeUpdate();}
        catch(SQLException e){System.out.println(e);}
    }

    static void setLocalInFileLoading(Connection connection) throws SQLException
    {
        PreparedStatement psSetLocalInfileLoading = connection.prepareStatement("SET GLOBAL local_infile = 1");
        try{psSetLocalInfileLoading.executeUpdate(); System.out.println("\nGlobal local infile set successfully");}
        catch (SQLException e) {System.out.println(e);}
    }

    static String loadDataInFileTable(String Filename, String Tablename)
    {
        return "LOAD DATA INFILE '" + Filename + "' INTO TABLE " + Tablename +
                " FIELDS TERMINATED BY '\t'" +
                " LINES TERMINATED BY '\n'" +
                " IGNORE 1 LINES";
    }

    //insert from select string for courses from schedule
    static String fromSelectcfs(String nameToTable, String nameFromTable)
    {
        return "INSERT INTO " +
                nameToTable +
                " (CourseID, CourseTitle, Department) SELECT CourseID, Title, Department FROM " +
                nameFromTable;
    }

    static String fromSelectctab(String NametoTable, String NamefromTable)
    {
            return "INSERT INTO " +
                    NametoTable +
                    " (CourseID, StudentID, SectionNumber, Year, Semester) SELECT CourseID, StudentID, SectionNumber, Year, Semester FROM "
                    + NamefromTable;
    }

    static void PopulateTable(Connection connection, String ddlPopulateTable) throws SQLException
    {
        PreparedStatement psPopulateTable = connection.prepareStatement(ddlPopulateTable);
        try{psPopulateTable.executeUpdate();}
        catch(SQLException e){System.out.println(e);}
    }

    static void InsertFromSelect(Connection connection, String nameToTable, String nameFromTable) throws SQLException
    {
        PreparedStatement psInsertFromSelect = connection.prepareStatement("INSERT INTO " + nameToTable + " SELECT * FROM " + nameFromTable);
        try{psInsertFromSelect.executeUpdate();}
        catch(SQLException e) {System.out.println(e);}
    }

    static void InsertFromSelect(Connection connection, String ddlInsertFromSelect) throws SQLException
    {
        PreparedStatement psInsertFromSelect = connection.prepareStatement(ddlInsertFromSelect);
        try{psInsertFromSelect.executeUpdate();}
        catch(SQLException e) {System.out.println(e);}
    }

    static void InsertRecord(Connection connection, String ddlInsertRecord) throws SQLException
    {
        PreparedStatement psInsertRecord = connection.prepareStatement(ddlInsertRecord);
        try{psInsertRecord.executeUpdate();}
        catch(SQLException e) {System.out.println(e);}
    }

    static void UpdateField(Connection connection, String ddlUpdateField) throws SQLException
    {
        PreparedStatement psUpdateField = connection.prepareStatement(ddlUpdateField);
        try{psUpdateField.executeUpdate();}
        catch(SQLException e) {System.out.println(e);}
    }

    static void DeleteRecord(Connection connection, String ddlDeleteRecord) throws SQLException
    {
        PreparedStatement psDeleteRecord = connection.prepareStatement(ddlDeleteRecord);
        try{psDeleteRecord.executeUpdate();}
        catch(SQLException e) {System.out.println(e);}
    }

    static ResultSet getTable(Connection connection, String Tablename) throws SQLException
    {
        ResultSet RS = null;
        PreparedStatement psGetTable = connection.prepareStatement("SELECT * FROM " + Tablename);
        try {RS = psGetTable.executeQuery();}
        catch(SQLException e) {System.out.println(e);}

        return RS;
    }
}