package com.example.assuignment4;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.ArcType;

public class MySlice {
    double x;
    double y;
    double radius;
    double angle;
    MyColor color;
    double radtodeg = Math.PI/180;

    MySlice(double x, double y, double diameter, double probability, MyColor color)
    {
        this.x = x;
        this.y = y;
        this.radius = diameter/2;
        this.angle = probability*360;
        this.color = color;
    }


    public void Stringto(String s){
        System.out.println("The slice with the coordinates (" + String.valueOf(x) + "," + String.valueOf(y) + "): " +
                "represents the letter " + s  + "with the color hexcode of " + color.Hex() + " and has a probability of " + String.valueOf(angle / 0.36) + "%");
    }



    //@Override
    public void draw(GraphicsContext gc, double startingangle, String l)
    {
        //for the slices
        gc.setFill(color.getColor());
        gc.fillArc(x, y, radius*2, radius*2, startingangle, angle, ArcType.ROUND);


        //for the legend
        double constant = 27;
        gc.setFill(MyColor.BLACK.getColor());;
        double MidAngle = startingangle + angle / 2;

        if(l.equals("All other letters") && 90 <= MidAngle && MidAngle <= 270) {constant += Math.abs(80 * Math.cos((MidAngle) * radtodeg));}

        double xText = (x + radius) + (radius + constant) * Math.cos(MidAngle * radtodeg) - 20;
        double yText = (y + radius) - (radius + constant) * Math.sin(MidAngle * radtodeg);

        gc.fillText(l + ": " + String.valueOf((double) (Math.round(angle / 0.36) / 10.0)) + "%", xText, yText);

    }
}
