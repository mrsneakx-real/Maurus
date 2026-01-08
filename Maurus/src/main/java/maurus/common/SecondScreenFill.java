package maurus.common;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;

public class SecondScreenFill {

    //takes a list of screens and adds a fullscreen rectangle (rectangle the size of the screen) to all execpt the primary one,
    // color can be customized

    public static List<Stage> secondScreenFill(String colorcode, List<Screen> screens ,Screen primaryScreen) {
    List<Stage> stages = new ArrayList<>();
    for (Screen screen : screens) {
        if (screen.equals(primaryScreen)) {
            continue; // skip the main monitor
        }
        Stage stage1 = new Stage();
        Rectangle2D screenSize1 = screen.getBounds();
        double width1 =  screenSize1.getWidth();
        double height1 = screenSize1.getHeight();

        Canvas canvas = new Canvas(width1, height1);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.web(colorcode));
        gc.fillRect(0, 0, width1, height1);

        StackPane root1 = new StackPane(canvas);
        Scene scene1 = new Scene(root1, width1, height1);
        stage1.setX(screenSize1.getMinX());
        stage1.setY(screenSize1.getMinY());
        stage1.setWidth(width1);
        stage1.setHeight(height1);
        stage1.setScene(scene1);
        stage1.initStyle(StageStyle.UNDECORATED);
        stage1.setAlwaysOnTop(true);
        stage1.setX(screenSize1.getMinX());
        stage1.setY(screenSize1.getMinY());
        stage1.show();
        stages.add(stage1);


    }
    return stages;
    }
}
