package maurus.features.ragebait;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
//ecnters the given UI elements
public class Centerstuff {
    public static void centerAll(Label titleLabel, Button b1, Button b2, Scene scene) {
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();
        double centerY = sceneHeight / 2;
        // Center title
        titleLabel.setLayoutX((sceneWidth - titleLabel.getWidth()) / 2);
        titleLabel.setLayoutY(centerY - 100);

        // Center buttons horizontally
        double spacing = 50;
        double totalButtonsWidth = b1.getWidth() + b2.getWidth() + spacing;
        b1.setLayoutX((sceneWidth - totalButtonsWidth) / 2);
        b2.setLayoutX(b1.getLayoutX() + b1.getWidth() + spacing);

        // Center buttons vertically

        b1.setLayoutY(centerY);
        b2.setLayoutY(centerY);
    }
}
