package explorer.window.vistools;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;

/**
 * Utility class for functions that apply transformations on javafx Group
 */
public class TransformUtils {

    /**
     * Applies a global rotation transformation to the specified 3D content group.
     * This method rotates the group around the given axis by the specified angle
     * and updates its transformation accordingly.
     *
     * @param contentGroup The group of 3D content to which the rotation transformation
     *                     will be applied.
     * @param axis The axis around which the 3D content group will be rotated, represented
     *             as a {@code Point3D}.
     * @param angle The rotation angle in degrees to apply to the 3D content group.
     *
     * SOURCE: copy from assignment06 WindowPresenter class
     */
    protected static void applyGlobalRotation(Group contentGroup, Point3D axis, double angle) {
        var currentTransform = contentGroup.getTransforms().getFirst();
        var rotate = new Rotate(angle, axis);
        currentTransform = rotate.createConcatenation(currentTransform);
        contentGroup.getTransforms().setAll(currentTransform);
    }

    private static double xPrev;
    private static double yPrev;
    /**
     * setups the mouse rotation functionality on:
     * @param pane in which the mouse rotation should be active
     * @param figure on which the rotation should be applied
     *
     * SOURCE: copy from assignment06 MouseRotate3D class
     */
    public static void setupMouseRotation(Pane pane, Group figure) {
        pane.setOnMousePressed(e -> {
            xPrev = e.getSceneX();
            yPrev = e.getSceneY();
        });
        pane.setOnMouseDragged(e -> {
            var dx = e.getSceneX() - xPrev;
            var dy = e.getSceneY() - yPrev;
            var axis = new Point3D(dy, -dx, 0).normalize();
            var angle = Math.sqrt(dx * dx + dy * dy) * 0.5; // based on the distance of the mouse movement

            TransformUtils.applyGlobalRotation(figure, axis, angle);

            xPrev = e.getSceneX();
            yPrev = e.getSceneY();
        });
    }
}
