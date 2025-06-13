package assignment06.window;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class MouseRotate3D {
    private static double xPrev;
    private static double yPrev;

    public static void setup(Pane pane, Group figure) {
        pane.setOnMousePressed(e -> {
            xPrev = e.getSceneX();
            yPrev = e.getSceneY();
        });
        pane.setOnMouseDragged(e -> {
            var dx = e.getSceneX() - xPrev;
            var dy = e.getSceneY() - yPrev;
            var axis = new Point3D(dy, -dx, 0).normalize();
            var angle = Math.sqrt(dx * dx + dy * dy) * 0.5; // based on the distance of the mouse movement

            WindowPresenter.applyGlobalRotation(figure, axis, angle);

            xPrev = e.getSceneX();
            yPrev = e.getSceneY();
        });
    }
}
