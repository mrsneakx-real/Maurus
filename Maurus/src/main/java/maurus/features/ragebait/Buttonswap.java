package maurus.features.ragebait;

import javafx.scene.control.Button;
//swaps the two buttons
public class Buttonswap {
    public static void swapButtons(Button b1, Button b2) {
        double tempX = b1.getLayoutX();
        double tempY = b1.getLayoutY();
        b1.setLayoutX(b2.getLayoutX());
        b1.setLayoutY(b2.getLayoutY());
        b2.setLayoutX(tempX);
        b2.setLayoutY(tempY);
    }
}
