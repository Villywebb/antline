package com.villywebb.antline;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;

public class Ant extends Circle {
    //0 is left 1 is right
    int moveDirection;
    int oldDirection;


    public Ant(Line line) {
        this.setRadius(7);
        this.setCenterY(line.getLineY());
        this.setCenterX(generateRandomXPosition(line));
        moveDirection = generateRandomMoveDirection();
    }

    public void die(ArrayList<Ant> ants){
        ants.remove(this);
        Main.root.getChildren().remove(this);
    }
    public void setRandomColor(){
        Random rand = new Random();
        int red = rand.nextInt(255);
        int green = rand.nextInt(255);
        int blue = rand.nextInt(255);
        this.setFill(Color.rgb(red, green, blue, 1));
    }
    public void setBlack(){
        this.setFill(Color.BLACK);
    }

    public void update() {
        if (moveDirection == 0) {
            this.setCenterX(this.getCenterX() - 0.5);
        } else {
            this.setCenterX(this.getCenterX() + 0.5);
        }
    }
    public void checkCollision(ArrayList<Ant> ants){
        if (isFullyColliding(ants)) {
           switchDirection();
        }
    }
    private void switchDirection(){
        if (moveDirection == 0) {
            moveDirection = 1;

        } else if (moveDirection == 1) {
            moveDirection = 0;
        }
    }

    private int generateRandomMoveDirection() {
        Random rand = new Random();
        return rand.nextInt(2);
    }

    private double generateRandomXPosition(Line line) {
        Random rand = new Random();
        return rand.nextInt((int) line.getWidth() + 1) + line.getX();
    }

    public Boolean isColliding(ArrayList<Ant> ants) {
        for (Ant ant : ants) {
            if (ant != this && this.intersects(ant.getLayoutBounds())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isFullyColliding(ArrayList<Ant> ants) {
        for (Ant ant : ants) {
            if (ant != this) {
                if (this.getCenterX() == ant.getCenterX()) {
                    return true;
                }
            }
        }
        return false;
    }
}
