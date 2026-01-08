package maurus.common;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ScreenFlicker {
    public static void screenFlicker(Stage primaryStage, double width, double height) {


        Canvas canvas1 = new Canvas(width, height);
        GraphicsContext gc = canvas1.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, width, height);
        StackPane root1 = new StackPane(canvas1);
        Scene scene2 = new Scene(root1, width, height);
        Stage stage2 = new Stage();
        stage2.setScene(scene2);
        stage2.setFullScreen(true);
        //shows a stage and hides it repeatedly while also doing the same to the primary class
        //Causes a lot of lag + bugs
        stage2.show();
        stage2.setFullScreen(false);
        stage2.hide();
        primaryStage.show();
        stage2.setFullScreen(true);
        stage2.show();
        stage2.hide();
        stage2.setFullScreen(false);
        stage2.hide();
        primaryStage.show();
        primaryStage.setFullScreen(true);
    }
}
