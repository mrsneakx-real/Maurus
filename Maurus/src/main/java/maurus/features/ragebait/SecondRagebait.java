package maurus.features.ragebait;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import javafx.util.Duration;
import maurus.common.block.KeyBlock;

public class SecondRagebait {

    public void start (Stage primaryStage, Runnable onDone){


        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(10), e -> {
                //blocks all input for a random time all 5 seconds a total of 6 times
            try {
                KeyBlock.BlockInput((int) (Math.random()*5000 + 100 ));//intervall
                System.out.println("secondRagebait");
            } catch (Exception ex) {
                System.out.println("secondRagebait Error");
                throw new RuntimeException(ex);
            }
        }));
        timeline.setCycleCount(6);//how may times its called
        timeline.play();
        timeline.setOnFinished(event -> {
            onDone.run();

        });
    }
}
