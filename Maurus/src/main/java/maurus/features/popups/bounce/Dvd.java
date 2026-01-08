package maurus.features.popups.bounce;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import maurus.App;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dvd {

    Scene scene;
    private List<Stage> blackScreens = new ArrayList<>();
    private double dx = 2;
    private double dy = 2;


    private static class Bouncer {
        Stage stage;
        double x, y;
        double dx, dy;
        double width, height;
        Rectangle2D bounds;

        Bouncer(Stage stage, double x, double y, double dx, double dy, //Bouncer Konstruktor
                double width, double height, Rectangle2D bounds) {
            this.stage = stage;
            this.x = x;
            this.y = y;
            this.dx = dx;
            this.dy = dy;
            this.width = width;
            this.height = height;
            this.bounds = bounds;
        }

        void update() { //Update function
            x += dx;
            y += dy;
            //moves the windows and bounces
            if (x <= bounds.getMinX() || x + width >= bounds.getMaxX()) {
                dx = -dx;
                x = Math.max(bounds.getMinX(), Math.min(bounds.getMaxX() - width, x));
            }

            if (y <= bounds.getMinY() || y + height >= bounds.getMaxY()) {
                dy = -dy;
                y = Math.max(bounds.getMinY(), Math.min(bounds.getMaxY() - height, y));
            }

            stage.setX(x);
            stage.setY(y);
        }
    }

    private final List<Bouncer> bouncers = new ArrayList<>();
    private final Random rand = new Random();


    public void start(Stage primaryStage, Runnable onDone) {
        for (Screen screen : Screen.getScreens()) {
            Rectangle2D bounds = screen.getBounds();
            Stage bgStage = new Stage(StageStyle.UNDECORATED);
            StackPane blackPane = new StackPane();
            blackPane.setStyle("-fx-background-color: black;");
            Scene blackScene = new Scene(blackPane, bounds.getWidth(), bounds.getHeight(), Color.BLACK);

            blackScreens.add(bgStage);
            bgStage.setScene(blackScene);
            bgStage.setX(bounds.getMinX());
            bgStage.setY(bounds.getMinY());
            bgStage.setWidth(bounds.getWidth());
            bgStage.setHeight(bounds.getHeight());
            bgStage.setAlwaysOnTop(false);
            bgStage.show();
        }

        Screen primaryScreen = Screen.getPrimary();
        Rectangle2D bounds = primaryScreen.getBounds();
        spawnBouncer(bounds);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (Bouncer b : new ArrayList<>(bouncers)) {
                    b.update();
                }
            }
        }.start();

        //close everything after a set time
        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(App.getAppclosetime()),actionEvent -> {
            for (Stage stage : blackScreens) {
                stage.hide();
                stage.close();
            }
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

        for (Stage stage: blackScreens ) {
            stage.show();
        }
    }

    private void spawnBouncer(Rectangle2D bounds) {
        Image image = new Image(getClass().getResourceAsStream("/bounce/dvd-screensaver.png")); // must be transparent PNG
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);

        Stage stage = new Stage(StageStyle.TRANSPARENT);
        stage.setAlwaysOnTop(true);

        StackPane root = new StackPane(imageView);
        scene = new Scene(root, image.getWidth(), image.getHeight(), Color.TRANSPARENT);
        stage.setScene(scene);
        scene.setCursor(Cursor.NONE);
        //where and with wich speed
        double x = bounds.getMinX() + rand.nextDouble() * (bounds.getWidth() - image.getWidth());
        double y = bounds.getMinY() + rand.nextDouble() * (bounds.getHeight() - image.getHeight());

        Bouncer b = new Bouncer(stage, x, y, dx, dy, image.getWidth(), image.getHeight(), bounds);
        bouncers.add(b);

        stage.setX(x);
        stage.setY(y);
        stage.show();
    }

}
