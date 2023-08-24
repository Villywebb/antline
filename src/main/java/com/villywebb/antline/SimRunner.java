package com.villywebb.antline;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SimRunner {
    Timeline tline;
    double time;

    public SimRunner(ArrayList<Ant> ants) {
        tline = new Timeline(new KeyFrame(Duration.millis(15), (ActionEvent event) -> {
            time = time + 0.015;

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
                tline.stop();
            }

        }));
        tline.setCycleCount(Timeline.INDEFINITE);
    }

    public void run() {
        tline.play();
    }
}
