package maurus.features.popups.ad;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import maurus.App;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Ad {

        public void start(Stage primaryStage, Runnable onDone) throws InterruptedException {

            int duration = 3;


            // Load your ad image
            List<Image> images = new CopyOnWriteArrayList<>();
            List<Stage> stages = new CopyOnWriteArrayList<>();
            //list of all images
            images.addAll(Arrays.asList(//makes it so that we can use addAll
                    new Image("https://www.chip.de/fec/assets/events/img/advent-banner-desktop-2025.gif"),
                    new Image("https://s0.2mdn.net/simgad/2633507719618481746"),
                    new Image("https://www.blackfriday.de/wp-content/uploads/nordpass-black-friday-2025.jpg"),
                    new Image("https://tpc.googlesyndication.com/simgad/11241445182044968444"),
                    new Image("https://tpc.googlesyndication.com/simgad/8694449401661222015"),
                    new Image("https://tpc.googlesyndication.com/simgad/8088114340121175386"),
                    new Image("https://migipedia.migros.ch/de/creme-dor-glacedose-jamaica/og"),
                    new Image("https://sw5-prod-media-files.s3.eu-central-1.amazonaws.com/media/image/11/1d/63/logo_avira.png"),
                    new Image("https://tpc.googlesyndication.com/simgad/16014862636851882090"),
                    new Image("https://tpc.googlesyndication.com/simgad/16337169116961387807"),
                    new Image("https://image.20min.ch/2025/08/30/6e46cc57-ff86-403d-b1f0-768cfce26260.jpg?auto=format%2Ccompress%2Cenhance&fit=max&w=1200&h=1200&rect=0%2C0%2C4000%2C2668&s=cdd561b9ec5bb66561c5889bf8a554d6"),
                    new Image("https://www.persoenlich.com/sites/default/files/filemanager/MIG_001694-00_Vuvh_Eistee_co_209x285_DE.jpg"),
                    new Image("https://www.vgt.ch/images/coop-zeitung_150825_philipona.jpg"),
                    new Image("https://www.persoenlich.com/media/cache/top/sites/default/files/wirz-ist-die-neue-ovo-agentur-5938.jpg"),
                    new Image(getClass().getResource("/Ads/AdImage.png").toExternalForm(), false),
                    new Image(getClass().getResource("/Ads/AdImage2.png").toExternalForm(), false),
                    new Image(getClass().getResource("/Ads/AdImage3.png").toExternalForm(), false),
                    new Image(getClass().getResource("/Ads/AdImage4.png").toExternalForm()),
                    new Image(getClass().getResource("/Ads/AdImage5.png").toExternalForm()),
                    new Image(getClass().getResource("/Ads/AdImage6.png").toExternalForm())

                    ));
            List<Screen> screens = new CopyOnWriteArrayList<>();
            screens.addAll(Screen.getScreens());
            // Loop through all available screens
            //beacuse we do it for each screen with radom delay they apper one after another
            for (int l = 0; l < screens.size(); l++) {
                Screen screen = screens.get(l);
                // For each screen, loop through all images
                for (int i = 0; i < images.size(); i++) {
                    Image image = images.get(i);
                    Timeline timeline = new Timeline(new KeyFrame(
                            Duration.seconds(duration * i + (Math.random() * 2)),//makes it so that they spawn a bt random
                            e -> {
                                createAdImage.adImage(image, stages, screen);
                            }
                    ));
                    timeline.play();
                    timeline.setCycleCount(1);//ensures it's only called once
                }
            }


            Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(App.getAppclosetime() + 0.5), e -> {
                //setup for second spam
                for (Stage stage: stages) {
                    stage.hide();
                    stage.close();
                }
                stages.clear();
                //quickly spams all the ads in a flash
                for (int l = 0; l < screens.size(); l++) {
                    Screen screen = screens.get(l);
                    // For each screen, loop through all images
                    for (int i = 0; i < images.size(); i++) {
                        Image image = images.get(i);
                        Timeline timeline1 = new Timeline(new KeyFrame(Duration.seconds(2), actionEvent -> {
                            createAdImage.adImage(image, stages, screen);
                            System.out.println("second spammmmmmmmmm");
                        }));
                        timeline1.play();
                        timeline1.setCycleCount(images.size());
                    }};
                // Close all stages after 30 seconds
                Timeline closeTimeline = new Timeline(new KeyFrame(Duration.seconds(30), ev -> {
                    for (Stage stage : stages) {
                        stage.hide();
                        stage.close();
                    }
                    stages.clear();
                    images.clear();
                    onDone.run();
                }));
                closeTimeline.play();
            }));
            timeline.play();
            timeline.setCycleCount(1);//ensures it's only called once
        }

    }


