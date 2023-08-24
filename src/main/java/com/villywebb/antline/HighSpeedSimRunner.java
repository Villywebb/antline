package com.villywebb.antline;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class HighSpeedSimRunner {
    private Timeline tline;
    private double time;

    public HighSpeedSimRunner(ArrayList<Ant> ants) {
        tline = new Timeline(new KeyFrame(Duration.millis(0.09), (ActionEvent event) -> {
            time = time + 0.00009;

            Main.timer.setText(""+ new DecimalFormat("#.##").format(time));
            for (Ant ant : ants) {
                ant.checkCollision(ants);
            }
            for (Ant ant : ants) {
                ant.update();
            }

            Boolean allOffLine = true;
            for (Ant ant : ants) {
                if (50 < ant.getCenterX() && ant.getCenterX() < 1050) {
                    allOffLine = false;
                }
            }
            if (allOffLine) {
                //restart
                tline.stop();
                if(time > 0.181){
                    Alert a = new Alert(Alert.AlertType.WARNING);
                    a.setHeaderText("OVER TIME");
                    a.setContentText("The ants took too long to cross the line");
                    a.show();
                }

                cleanUp(ants);
                Main.antAdder();
                if (Main.color.isSelected()) {
                    for (Ant ant : ants) {
                        ant.setRandomColor();
                    }
                } else {
                    for (Ant ant : ants) {
                        ant.setBlack();
                    }
                }
                tline.playFromStart();
            }

        }));
        tline.setCycleCount(Timeline.INDEFINITE);
    }
    private void cleanUp(ArrayList<Ant> ants ){
        time = 0;
        for (int i = 0; i < ants.size(); i++) {
            ants.get(i).die(ants);
        }

    }

    public void run() {
        tline.play();
    }
}
