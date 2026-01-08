package maurus.features.lagmachine;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import maurus.App;

public class Heavylag {

    private boolean runner = true;
    private double sum1, calc1, calc2;

    public void start(Stage primaryStage, Runnable onDone) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root);

        // Add key handler: stop when "L" is pressed
        scene.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode().toString().equals("L")) {
                runner = false;
                System.out.println("Key L pressed! Exiting loop.");
            }
        });

        // Run loop in background
        int cores = Runtime.getRuntime().availableProcessors();
        System.out.println("Using " + cores + " cores");

        for (int i = 0; i < cores; i++) {
            new Thread(() -> {
                while (true) {
                    double x = Math.random() * Math.random();
                    double y = Math.sqrt(x);
                }
            }).start();
        }
        Timeline timer = new Timeline(
                new KeyFrame(Duration.minutes(App.getAppclosetime()), event -> {
                    onDone.run();
                })
        );
        timer.setCycleCount(1);//ensures it only runs once
        timer.play();
    }
}