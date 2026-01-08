package maurus.features.popups.ad;


import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


import java.util.List;


public class createAdImage {


   public static List<Stage>  adImage(Image image, List<Stage> stages, Screen screen) {



       ImageView adImage = new ImageView(image);

       Stage adStage = new Stage();


       double screenWidth = screen.getBounds().getWidth();
       double screenHeight = screen.getBounds().getHeight();

       double imgH = image.getHeight();
       double imgW = image.getWidth();
        //so thhey dont clip out of screen
       double offsetX = screen.getBounds().getMinX();
       double offsetY = screen.getBounds().getMinY();

       adImage.setPreserveRatio(true);
       stages.add(adStage);
       Pane root = new Pane(adImage);

       Scene scene = new Scene(root, imgW, imgH);

       adStage.setScene(scene);
       adStage.setTitle("Ad");
       adStage.initStyle(StageStyle.UNDECORATED);  // optional: remove window borders
       adStage.setAlwaysOnTop(true);               // keep on top

       //random position:
       double randomWidth = offsetX + Math.random() * (screenWidth - scene.getWidth());
       double randomHeight = offsetY + Math.random() * (screenHeight - scene.getHeight());

       // Position window at top-left (0,0)
       adStage.setX(randomWidth);
       adStage.setY(randomHeight);
       adImage.setLayoutX(0);
       adImage.setLayoutY(0);


       adStage.show();

       return stages;
   }
}
