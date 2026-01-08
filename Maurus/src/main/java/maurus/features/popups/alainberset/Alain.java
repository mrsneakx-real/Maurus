package maurus.features.popups.alainberset;

import javafx.animation.PauseTransition;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane; // <-- Needed
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;



public class Alain {

    private static final String IMAGE_PATH = "/ALAIN/ALAIN_BERSET.jpg";
    private List<String> video_list = new CopyOnWriteArrayList<>();
    private String VIDEO_PATH;
    private static final int IMAGE_DISPLAY_SECONDS = 5;



    public void start(Stage primaryStage, Runnable onDone) {
        List<Screen> screens = Screen.getScreens();
        List<Stage> stages = new ArrayList<>();

        video_list.addAll(Arrays.asList(
                "/ALAIN/ALAIN_BESRET.mp4",
                "/ALAIN/ALAIN_TECHNO.mp4",
                //"/ALAIN/Bleiben_sie_Zuhause.mp4",
                "/ALAIN/Homeoffice.mp4",
                "/ALAIN/Buendnerfleisch.mp4"
        ));

        int wich_vid = (int) (Math.random()*video_list.size());
        VIDEO_PATH = video_list.get(wich_vid);


        Media media = new Media(getClass().getResource(VIDEO_PATH).toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);




        for (Screen screen : screens) {
            Stage stage = new Stage();
            stages.add(stage);
            stage.setX(screen.getVisualBounds().getMinX());
            stage.setY(screen.getVisualBounds().getMinY());
            stage.setWidth(screen.getVisualBounds().getWidth());
            stage.setHeight(screen.getVisualBounds().getHeight());

            // Display Image first
            Image image = new Image(getClass().getResource(IMAGE_PATH).toExternalForm());
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(stage.getWidth());
            imageView.setFitHeight(stage.getHeight());

            StackPane imageRoot = new StackPane(imageView); // Wrap in container
            Scene imageScene = new Scene(imageRoot);
            stage.setScene(imageScene);
            stage.initStyle(StageStyle.UNDECORATED); // no title bar
            stage.setX(screen.getVisualBounds().getMinX());
            stage.setY(screen.getVisualBounds().getMinY());
            stage.setWidth(screen.getVisualBounds().getWidth());
            stage.setHeight(screen.getVisualBounds().getHeight());
            stage.setAlwaysOnTop(true);
            stage.show();

            // After delay, switch to video
            PauseTransition delay = new PauseTransition(Duration.seconds(IMAGE_DISPLAY_SECONDS));//is a animation that just waits for a set time
            delay.setOnFinished(event -> {//when waiting is finished
                System.out.println(VIDEO_PATH + " video");
                MediaView mediaView = new MediaView(mediaPlayer);
                mediaView.setFitWidth(stage.getWidth());
                mediaView.setFitHeight(stage.getHeight());
                mediaView.setPreserveRatio(true);

                StackPane videoRoot = new StackPane(mediaView); // MEdia view in Pane
                Scene videoScene = new Scene(videoRoot);
                stage.setScene(videoScene);

                // after vid finished
                mediaPlayer.setOnEndOfMedia(() -> {
                    mediaPlayer.dispose(); // end media player etc
                    for (Stage stage1 : stages){//remove stages
                        stage1.hide();
                        stage1.close();

                    }
                    onDone.run(); // go back to the openrandomclass
                });

                mediaPlayer.play();
            });
            delay.play();
        }
    }
}
