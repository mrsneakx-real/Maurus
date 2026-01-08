package maurus.features.ragebait;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;

public class ButtonMove {
    public static void movebutton(Button button1, Scene scene, Stage primaryStage,int yesClickCount, int dodgeThreshold) {
        //checks if ere at the stage of the moving button
        if (yesClickCount < dodgeThreshold) return;

        double windowX = primaryStage.getX();
        double windowY = primaryStage.getY();

        double mouseX = MouseInfo.getPointerInfo().getLocation().getX() - windowX;
        double mouseY = MouseInfo.getPointerInfo().getLocation().getY() - windowY;

        double buttonX = button1.getLayoutX() + button1.getWidth() / 2;
        double buttonY = button1.getLayoutY() + button1.getHeight() / 2;

        double dx = buttonX - mouseX;
        double dy = buttonY - mouseY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        //actual logic
        if (distance < 100) {
            double moveX = dx / distance * 30;
            double moveY = dy / distance * 30;

            double newX = Ragebait.clamp(button1.getLayoutX() + moveX, 0, scene.getWidth() - button1.getWidth());
            double newY = Ragebait.clamp(button1.getLayoutY() + moveY, 0, scene.getHeight() - button1.getHeight());

            button1.setLayoutX(newX);
            button1.setLayoutY(newY);
        }

    }
}
