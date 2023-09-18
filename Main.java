package com.example.assuignment4;

import javafx.collections.FXCollections;
import javafx.scene.canvas.GraphicsContext;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import java.lang.Math;
import javax.swing.*;

public class Main extends Application {

    public class HistogramPieChart {
        double x, y, radius;
        public Map<Character, Integer> frequency = new HashMap<Character, Integer>();
        double cc = Math.PI / 180;
        String TABLENAME = "AggregateGrades";

        private static final char[] g = {'A', 'B', 'C', 'D', 'F', 'W'};

        public HistogramPieChart(double x, double y, double diameter) {
            this.x = x;
            this.y = y;
            this.radius = diameter / 2;

            frequency = StudentsDatabase.AggregateGrades.getAggregateGrades(TABLENAME);
        }

        public void draw(GraphicsContext gc) {
            double totalAngle = 0;

            double sum = frequency.values().stream().mapToInt(Integer::intValue).sum();

            for (int i = 0; i < 6; i++) {
                if (frequency.get(g[i]) == null) {
                    continue;
                }

                double angle = 360 * ((double) frequency.get(g[i]) / sum);

                drawSlice(gc, angle, totalAngle, String.valueOf(g[i]), MyColor.COLORS[i]);

                totalAngle += angle;
            }
        }

        public void drawSlice(GraphicsContext gc, double angle, double startingAngle, String s, MyColor color) {
            gc.setFill(color.getColor());
            gc.fillArc(x, y, radius * 2, radius * 2, startingAngle, angle, ArcType.ROUND);

            gc.setFill(MyColor.BLACK.getColor());

            double middleAngle = startingAngle + angle / 2, offset = 32.5;

            if (s.equals("All other letters") && 180 <= middleAngle && middleAngle <= 270) {
                offset += Math.abs(80 * Math.cos((middleAngle) * cc));
            }

            double x_pos = (x + radius) + (radius + offset) * Math.cos(middleAngle * cc) - 25;
            double y_pos = (y + radius) - (radius + offset) * Math.sin(middleAngle * cc);

            gc.fillText(s + ", " + String.valueOf((double) (Math.round(angle / 0.036) / 100.0)) + "%", x_pos, y_pos);
        }
    }

    public static void main(String args[]) throws SQLException {
        StudentsDatabase DB = new StudentsDatabase();

        //Schedule table
        String ddlCreateTable;
        String Filename, Tablename;
        Filename = "C:/ProgramData/MySQL/tmp/scheduleSpring2022.txt";
        Tablename = "Schedule";
        ddlCreateTable = StudentsDatabaseInterface.ddlCreateTableSchedule;
        StudentsDatabase.Schedule schedule1 = DB.new Schedule(ddlCreateTable, Filename, Tablename);

        //Courses table
        String nameToTable = "Courses";
        String nameFromTable = "Schedule";
        String nameofTable = "Courses";
        ddlCreateTable = StudentsDatabaseInterface.ddlCreateTableCourses;
        StudentsDatabase.Courses courses1 = DB.new Courses(ddlCreateTable, nameofTable, nameToTable, nameFromTable);

        //Students table
        String nameTable = "Students";
        ddlCreateTable = StudentsDatabaseInterface.ddlCreateTableStudents;
        StudentsDatabase.Students students1 = DB.new Students(ddlCreateTable, nameTable);

        //Classes table
        String TableNamec = "Classes";
        ddlCreateTable = StudentsDatabaseInterface.ddlCreateTableClasses;
        StudentsDatabase.Classes classes1 = DB.new Classes(ddlCreateTable, TableNamec);

        //AggregateGrades table
        String NamefromTable = "Classes";
        String NametoTable = "AggregateGrades";
        String TABLENAME = "AggregateGrades";
        StudentsDatabase.AggregateGrades ag1 = DB.new AggregateGrades(NametoTable, NamefromTable);
        Map<Character, Integer> mag = StudentsDatabase.AggregateGrades.getAggregateGrades(TABLENAME);
        System.out.println(mag);

        launch(args);
    }



    public HBox getWindowContents(Canvas canvas) {
        VBox Text = new VBox(30);
        VBox Fields = new VBox(20);
        Text.setPadding(new Insets(10));
        Fields.setPadding(new Insets(10));

        Text.getChildren().addAll(editStudentGrade, addStudent, FirstName, LastName, Gender, EMPLID, Email, Grade, Error);
        Fields.getChildren().addAll(button1, button2, FirstNameInput, LastNameInput, GenderInput, EMPLIDInput, EmailInput, GradeInput);

        HBox userInterface = new HBox(20);
        userInterface.setPadding(new Insets(0));

        userInterface.getChildren().addAll(Text, Fields);

        VBox left = new VBox(20);
        left.setPadding(new Insets(0));

        left.getChildren().addAll(userInterface, pieChart, canvas);

        HBox completed = new HBox(20);
        completed.setPadding(new Insets(0));

        completed.getChildren().addAll(left, StudentsDatabase.table());

        return completed;
    }

    public Label editStudentGrade = new Label();
    public Label addStudent = new Label();
    public Label FirstName = new Label();
    public Label LastName = new Label();
    public Label Gender = new Label();
    public Label EMPLID = new Label();
    public Label Email = new Label();
    public Label Grade = new Label();
    public Label Error = new Label();
    public Label pieChart = new Label();

    TextField FirstNameInput = new TextField();
    TextField LastNameInput = new TextField();
    TextField GenderInput = new TextField();
    TextField EMPLIDInput = new TextField();
    TextField EmailInput = new TextField();
    TextField GradeInput = new TextField();

    public Button button2 = new Button("Add student");
    public Button button1 = new Button("Edit Grade");


    public boolean GenderFormatting(String gender) {return gender.equals("M") || gender.equals("F") || gender.equals("U");}
    public boolean EMPLIDFormatting(String empID) {
        try {int d = Integer.parseInt(empID);}
        catch (NumberFormatException numberFormatException) {return false;}

        return empID.length() == 9;
    }
    public boolean GradeFormatting(String grade) {return grade.equals("A") || grade.equals("B") || grade.equals("C") || grade.equals("D") || grade.equals("F") || grade.equals("W");}

    public void start(Stage stage) {

        double W = 1280;
        double H = 720;
        double diameter = Math.min(W, H) / 1.5;

        Scene scene1;
        Stage s = stage;
        s.setTitle("Student Database Manager");

        String[] items = {"PieChart", "Table", "Edit/Add"};
        ListView<String> listView = new ListView<>(FXCollections.observableArrayList(items));


        TextField sinput = new TextField();
        Label label = new Label();
        Label label2 = new Label();
        label.setText(null);
        label2.setText("What would you like to see? (PieChart or Table or Edit): ");

        //piechart layout
        Group root = new Group();
        Scene scene2 = new Scene(root, W, H);
        Canvas canvas = new Canvas(diameter, H);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //table layout
        Group root2 = new Group();
        Group root3 = new Group();

        Scene menu = new Scene(root3, W, H);
        Canvas canvas2 = new Canvas(diameter, H);

      /*  VBox layout = new VBox(20);
        layout.setPadding(new Insets(20, 15, 20, 15));
        layout.getChildren().addAll(label2, sinput, button, label);
        scene1 = new Scene(layout, 400, 175);*/


        Button button = new Button("Display");

        button.setOnAction(event -> {
            String selectedItem = listView.getSelectionModel().getSelectedItem();
            if (selectedItem == "PieChart") {
                label.setText("Displaying Pie Chart");
                HistogramPieChart piechart1 = new HistogramPieChart(100, 50, diameter/2);
                s.setScene(scene2);
                piechart1.draw(gc);
                StudentsDatabase.table();
                root.getChildren().add(canvas);
                s.show();
            }
            else if (selectedItem == "Table") {
                label.setText("Displaying Table");
                VBox vBox = new VBox();
                vBox.getChildren().add(StudentsDatabase.table());
                Scene tmpscene = new Scene(vBox, W, H);
                s.setScene(tmpscene);
                s.show();
            }
            else if (selectedItem == "Edit/Add") {
                editStudentGrade.setText("Enter Grade and EMPLID to edit");
                addStudent.setText("Add student");
                FirstName.setText("First name");
                LastName.setText("Last name");
                Gender.setText("Gender (M / F / U)");
                EMPLID.setText("EMPLID");
                Email.setText("Email");
                Grade.setText("Grade (A / B / C / D / F / W)");
                Error.setText(null);

                pieChart.setFont(new Font("Arial", 20));

                FirstNameInput.setPrefWidth(W / 5.75);
                LastNameInput.setPrefWidth(W / 5.75);
                GenderInput.setPrefWidth(W / 5.75);
                EMPLIDInput.setPrefWidth(W / 5.75);
                EmailInput.setPrefWidth(W / 5.75);
                GradeInput.setPrefWidth(W / 5.75);

                HistogramPieChart pie = new HistogramPieChart(150, 50, diameter / 2);
                pie.draw(gc);

                root3.getChildren().add(getWindowContents(canvas2));

                button2.setOnAction(f-> {
                    if(EMPLIDFormatting(EMPLIDInput.getText()) && GenderFormatting(GenderInput.getText()) && GradeFormatting(GradeInput.getText())) {
                        try {
                            StudentsDatabase.Students.InsertRecord(Integer.parseInt(EMPLIDInput.getText()), FirstNameInput.getText(), LastNameInput.getText(), EmailInput.getText(), GenderInput.getText().charAt(0));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            StudentsDatabase.Classes.InsertRecord("22100 P", Integer.parseInt(EMPLIDInput.getText()), 32132, 2021, "Spring", GradeInput.getText().charAt(0));
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        try {
                            StudentsDatabase.AggregateGrades.UpdateAggregateGrades(pie.TABLENAME);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        root3.getChildren().clear();
                        gc.clearRect(0, 0, W, H);

                        HistogramPieChart updatedPieChart = new HistogramPieChart(75, 50, diameter / 2);
                        // System.out.println("Updated PieChart: " + updatedPieChart);
                        //updatedPieChart.draw(gc);
                        root3.getChildren().add(getWindowContents(canvas2));
                    }

                    else {Error.setText("Invalid entry.");}
                });

                button1.setOnAction(g-> {
                    try{
                        StudentsDatabase.Classes.UpdateGrade(Integer.parseInt(EMPLIDInput.getText()), GradeInput.getText().charAt(0));
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    root3.getChildren().clear();
                    gc.clearRect(0, 0, W, H);

                    HistogramPieChart updatedPiechart = new HistogramPieChart(75, 50, diameter / 2);
                    // updatedPiechart.draw(gc);
                    //System.out.println("Updated PieChart: " + updatedPiechart);

                    root3.getChildren().add(getWindowContents(canvas2));

                });
                
                stage.setScene(menu);
                stage.show();
            }
            });


                     /*  Button button3 = new Button("Back");
                button3.setOnAction(h-> {
                    s.setScene(scene1);
                    s.show();
                });*/

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 15, 20, 15));
        layout.getChildren().addAll(label2, listView, button, label);
        scene1 = new Scene(layout, 400, 175);

        s.setScene(scene1);
        s.show();}}