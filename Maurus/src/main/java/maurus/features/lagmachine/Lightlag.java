package maurus.features.lagmachine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import maurus.App;

public class Lightlag {

    private boolean runner = true;
    private double sum1, calc1, calc2;

    public void start(Stage primaryStage, Runnable onDone) {

        // Run loop in background
        int cores = 0;
        int coresmath = Runtime.getRuntime().availableProcessors();
        if (coresmath >= 16) {
            cores = 6;
        } else if (coresmath >= 12) {
            cores = 5;
        } else if (coresmath >= 8) {
            cores = 3;
        } else {
            cores = 1;
        }
        System.out.println("Using " + cores + " cores");

        for (int i = 0; i < cores; i++) {
            new Thread(() -> {
                while (runner) {
                    double x = Math.random() * Math.random();
                    double y = Math.sqrt(x);
                }
            }).start();
        }
        Timeline timer = new Timeline(
                new KeyFrame(Duration.minutes(App.getAppclosetime()), event -> {
                    onDone.run();
                    runner = false;
                })
        );
        timer.setCycleCount(1);//ensures it's only called once
        timer.play();
    }
}