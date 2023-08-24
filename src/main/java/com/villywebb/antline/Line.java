package com.villywebb.antline;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class Line extends Rectangle {
    private double x;
    private double y;
    public Line (double x, double y, double length, Group root){
        this.x = x;
        this.y = y;
        this.setWidth(length);
        this.setX(x);
        this.setY(y - 1);
        this.setHeight(2);
        root.getChildren().add(this);

    }
    public double getLineY() {
        return this.y;
    }
}
