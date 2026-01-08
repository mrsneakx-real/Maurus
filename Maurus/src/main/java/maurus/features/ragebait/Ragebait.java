package maurus.features.ragebait;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.input.KeyCombination;
import javafx.util.Duration;
import maurus.App;
import maurus.common.block.KeyBlock;
import maurus.common.block.TaskkillTaskMngr;
import maurus.common.SecondScreenFill;

import java.awt.*;
import java.util.List;

import static maurus.features.ragebait.ButtonMove.movebutton;
import static maurus.features.ragebait.Buttonswap.swapButtons;


public class Ragebait extends Application {
    private AnimationTimer animationTimer;
    private int yesClickCount = 0;
    private final int dodgeThreshold = 5;
    private List<Screen> screens = Screen.getScreens();
    private boolean i = false;
    private int i1 = 0;


    @Override
    public void start(Stage primaryStage) throws AWTException {
        //design
        Label titleLabel = new Label("Do you want to exit?");
        titleLabel.setFont(new Font("Arial Bold", 30));
        titleLabel.setTextFill(Color.DARKBLUE);

        Button button1 = new Button("Yes");
        Button button2 = new Button("No");

        String buttonStyle = "-fx-background-color: linear-gradient(#67ba48, #3f8a21);" +
                "-fx-text-fill: white; -fx-font-size: 18px; -fx-padding: 15 30 15 30; -fx-background-radius: 12;";
        button1.setStyle(buttonStyle);
        button2.setStyle(buttonStyle);

        Pane root = new Pane();
        root.getChildren().addAll(titleLabel, button1, button2);

        Scene scene = new Scene(root, 800, 600);

        // Swap buttons
        button1.setOnAction(e -> {
            yesClickCount++;
            swapButtons(button1, button2);
            if (yesClickCount >= dodgeThreshold && !i) {
                titleLabel.setText("Really?");
            }
            if (yesClickCount >= dodgeThreshold * 2 && !i) {//bedingungen
                i = true;
                titleLabel.setText(":(");
                Timeline timer = new Timeline(
                        new KeyFrame(Duration.seconds(5), event -> {
                            titleLabel.setText("I thought you were my Friend...");
                        }),
                    new KeyFrame(Duration.seconds(10),ev -> {
                        titleLabel.setText("But in the end it seems as if i was wrong");
                }),
                    new KeyFrame(Duration.seconds(15),ev -> {
                        titleLabel.setText("If i cant be happy, then neither can you!");
                    }),
                    new KeyFrame(Duration.seconds(17),ev -> {
                        titleLabel.setText("You are gonna be stuck in here with me for Eternity");
                })


                );

                timer.setCycleCount(1);//ensures its only called once
                timer.play();
            }
        });

        button2.setOnAction(e -> swapButtons(button1, button2));

        //set on key events with exit kea K
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case K:
                    System.exit(0);
                    break;
                case CONTROL:
                    try {
                        KeyBlock.BlockInput(1000);
                    } catch (Exception e) {
                        System.out.println("KeyBlock.BlockInput(1)");
                        throw new RuntimeException(e);

                    }
                    break;
                case ALT:
                    try {
                        KeyBlock.BlockInput(5000);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }

        });
        //creates robot to movethe mouse later
        Robot robot = new Robot();
        Rectangle2D primaryBounds = Screen.getPrimary().getVisualBounds();

        animationTimer = new  AnimationTimer() {
            @Override
            public void handle(long now) {
                //moves the button and makes robot limit mouse, is performance intensive
                movebutton(button1, scene, primaryStage, yesClickCount, dodgeThreshold);
                Timeline mouseClampTimeline = new Timeline(//so that it doesnt fire every animation frame but only all 100 miliseconds
                    new KeyFrame(Duration.millis(100), event -> {
                        Point p = MouseInfo.getPointerInfo().getLocation();//do not get info every time only all 100 milisec
                        int margin = 40;
                        //check the bounds
                        int x = (int) Math.max(primaryBounds.getMinX() + margin,
                                Math.min(p.getX(), primaryBounds.getMaxX() - margin));
                        int y = (int) Math.max(primaryBounds.getMinY() + margin,
                                Math.min(p.getY(), primaryBounds.getMaxY() - margin));

                        // Only move the mouse if it's really outside bounds
                        if (p.x < primaryBounds.getMinX() + margin || p.x > primaryBounds.getMaxX() - margin ||
                                p.y < primaryBounds.getMinY() + margin || p.y > primaryBounds.getMaxY() - margin) {
                            robot.mouseMove(x, y);
                        }
                    })
                );
                mouseClampTimeline.setCycleCount(Timeline.INDEFINITE);
                mouseClampTimeline.play();
                if (i1 > 100 ){
                TaskkillTaskMngr.taskkill();
                    }
                i1++;
            }
        };
        animationTimer.start();


        //JavaFX
        List<Stage> stages = SecondScreenFill.secondScreenFill("#000000",screens, Screen.getPrimary() );
        primaryStage.setScene(scene);
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setFullScreen(false);
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.requestFocus();
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setMaximized(true);
        primaryStage.show();

        primaryStage.toFront();
        primaryStage.setOnHidden(e -> {
            e.consume();
        });
        // Center everything after layout
        Platform.runLater(() -> Centerstuff.centerAll(titleLabel, button1, button2, scene));
        //after set time close
        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(App.getAppclosetime()), actionEvent -> {
          primaryStage.close();
          primaryStage.hide();
          for (Stage s : stages) {
              s.hide();
              s.close();
          }
          animationTimer.stop();
        })
        );
        timeline.setCycleCount(1);//ensures it's only called'
        timeline.play();

    }

    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }
}
