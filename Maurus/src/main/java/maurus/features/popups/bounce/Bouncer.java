package maurus.features.popups.bounce;

import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;

public class Bouncer {
    Stage stage;
    double x, y;
    double dx, dy;
    double width, height;
    Rectangle2D bounds;


    Bouncer(Stage stage, double x, double y, double dx, double dy,
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

    void update() {
        x += dx;
        y += dy;
        //updates the bouncers and makes them bounce
        // bounce horizontally
        if (x <= bounds.getMinX() || x + width >= bounds.getMaxX()) {
            dx = -dx;
            x = Math.max(bounds.getMinX(), Math.min(bounds.getMaxX() - width, x));//ensures window is always on screen
        }

        // bounce vertically
        if (y <= bounds.getMinY() || y + height >= bounds.getMaxY()) {
            dy = -dy;
            y = Math.max(bounds.getMinY(), Math.min(bounds.getMaxY() - height, y));
        }

        stage.setX(x);
        stage.setY(y);
    }
}
