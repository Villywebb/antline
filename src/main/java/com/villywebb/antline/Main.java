package com.villywebb.antline;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    public static int numAnts = 20;
    public static Group root = new Group();
    public static Label timer;
    public static CheckBox color = new CheckBox();
    public static Line line;
    static ArrayList<Ant> ants = new ArrayList<>();
    Boolean highSpeed = false;
    boolean firstPress = true;

    public static void main(String[] args) {
        launch();
    }

    public static void antAdder() {
        Ant ant;
        for (int i = 0; i < numAnts; i++) {
            ant = new Ant(line);
            if (!ant.isColliding(ants)) {
                ants.add(ant);
                root.getChildren().add(ant);
            }
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        setup(stage);
    }

    public void setup(Stage stage) {
        line = new Line(50, 200, 1100 - (50 * 2), root);
        Button play = new Button("Play");

        CheckBox highSpeedSim = new CheckBox();


        color.setText("Color");
        color.setLayoutX(650);
        color.setLayoutY(300);
        color.selectedProperty().addListener(new ChangeListener<Boolean>() {

            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    for (Ant ant : ants) {
                        ant.setRandomColor();
                    }
                } else {
                    for (Ant ant : ants) {
                        ant.setBlack();
                    }
                }
            }
        });

        highSpeedSim.setLayoutX(750);
        highSpeedSim.setLayoutY(300);
        highSpeedSim.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    highSpeed = true;
                } else {
                    highSpeed = false;
                }
            }
        });
        timer = new Label();
        timer.setLayoutX(500);
        timer.setLayoutY(300);
        root.getChildren().addAll(timer, color, highSpeedSim, play);
        antAdder();
        play.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                play();
            }
        });


        //-----
        Scene scene = new Scene(root, 1100, 400);
        stage.setTitle("AntTest");
        stage.setScene(scene);
        stage.show();

    }

    public void cleanUp() {
        for (int i = 0; i < ants.size(); i++) {
            ants.get(i).die(ants);
        }

    }
    SimRunner simRunner = null;
    public void play() {

        if (highSpeed) {
            HighSpeedSimRunner highSpeedSimrunner = new HighSpeedSimRunner(ants);
            highSpeedSimrunner.run();
        } else {

            if (firstPress) {
                simRunner = new SimRunner(ants);
                simRunner.run();
                firstPress = false;
            } else {

                simRunner.time = 0;
                simRunner.tline.stop();

                cleanUp();
                antAdder();

                if (Main.color.isSelected()) {
                    for (Ant ant : ants) {
                        ant.setRandomColor();
                    }
                } else {
                    for (Ant ant : ants) {
                        ant.setBlack();
                    }
                }
                simRunner.run();
            }

        }
    }
}