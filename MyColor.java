package com.example.assuignment4;

import javafx.scene.paint.Color;

public enum MyColor {
    //Basic colors
    BLACK(0,0,0,255),
    WHITE(255,255,255,255),
    RED(255,0,0,255),
    GREEN(0,255,0,255),
    BLUE(0,0,255,255),
    YELLOW(255,255,0,255),
    DARKSEAGREEN(143,188,143,255),
    LIGHTCORAL(240,128,128,255),
    ORANGE(255,165,0,255),
    ROYALBLUE(65,105,225,255),
    PURPLE(128,0,128,255),
    MAGENTA(255,0,255,255),
    CHOCOLATE(210,105,30,255),
    SLATEGRAY(112,128,144,255),
    LIME(0,255,0,255),
    DARKCYAN(0,139,139,255),
    SADDLEBROWN(139,69,19,255),
    ROSYBROWN(188,143,143,255),
    PAPAYAWHIP(255,239,213,255),
    LAVENDER(230,230,250,255),
    PALEYELLOW(255,255,75,255),
    STEELBLUE(70,130,180,255),
    GREY(128,128,128,255),
    DARKKHAKI(189,183,107,255),
    SANDYBROWN(244,164,96,255),
    LIGHTSALMON(255,160,122,255),
    PALEORANGE(255,178,102,255),
    OLIVEDRAB(107,142,35,255);

    int rgba;
    Color mycolor;

    //Constructor
    MyColor(int r, int g, int b, int a) {
        this.rgba = (a << 24) & 0xFF000000 |
                (r << 16) & 0x00FF0000 |
                (g << 8) & 0x0000FF00 |
                b;
        this.mycolor = Color.rgb(r,g,b,(double)(a/255));
    }

    //Returns color
    public Color getColor() {
        return mycolor;
    }

    //Returns hexadecimal representation of color
    public String Hex() {
        return "#" + Integer.toHexString(rgba).toUpperCase();
    }

    //Stores various colors for easy use
    public static MyColor[] COLORS = new MyColor[] {
            MyColor.RED,
            MyColor.GREEN,
            MyColor.BLUE,
            MyColor.YELLOW,
            MyColor.DARKSEAGREEN,
            MyColor.LIGHTCORAL,
            MyColor.ORANGE,
            MyColor.ROYALBLUE,
            MyColor.PURPLE,
            MyColor.MAGENTA,
            MyColor.CHOCOLATE,
            MyColor.SLATEGRAY,
            MyColor.LIME,
            MyColor.DARKCYAN,
            MyColor.SADDLEBROWN,
            MyColor.ROSYBROWN,
            MyColor.PAPAYAWHIP,
            MyColor.LAVENDER,
            MyColor.PALEYELLOW,
            MyColor.STEELBLUE,
            MyColor.GREY,
            MyColor.DARKKHAKI,
            MyColor.SANDYBROWN,
            MyColor.LIGHTSALMON,
            MyColor.PALEORANGE,
            MyColor.WHITE,
            MyColor.OLIVEDRAB,
    };
}
