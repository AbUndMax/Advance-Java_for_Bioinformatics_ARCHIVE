package assignment06.window;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;

/**
 * The MyCamera class extends the PerspectiveCamera class, providing a customized
 * configuration suitable for managing a 3D scene. This class supports operations
 * such as resetting the camera's position and focusing on the entirety of a given 3D figure.
 */
public class MyCamera extends PerspectiveCamera {

    // reset Position the camera returns to after pressing the reset button
    private double resetPositionInZ = -400;

    /**
     * Constructs an instance of the MyCamera class, extending PerspectiveCamera
     * with specific initial configuration.
     *
     * This camera is configured to:
     * - Start with a field of view set by the superclass constructor with the 'true' parameter, enabling vertical field of view.
     * - Set a far clipping plane distance of 10,000 units to control the maximum visible distance.
     * - Set a near clipping plane distance of 0.1 units to control the minimum visible distance.
     * - Position the camera away from the origin (in the Z-axis) by applying a translation
     *   defined by a predefined reset position, allowing a default overview of the 3D scene.
     */
    public MyCamera() {
        super(true);
        this.setFarClip(10000);
        this.setNearClip(0.1);
        this.setTranslateZ(resetPositionInZ); // back away from the origin ...
    }

    /**
     * Resets the camera's position in the 3D scene to its initial state.
     * This method updates the camera's Z-axis translation to the predefined reset position,
     * which is determined by the current value of the resetPositionInZ field.
     */
    public void resetView(){
        this.setTranslateZ(resetPositionInZ);
    }

    /**
     * Adjusts the camera's position and view to focus on the entirety
     * of the provided 3D figure. The method calculates the optimal
     * distance required to encompass the entire figure within the
     * camera's field of view (FOV) and adjusts the camera's position
     * accordingly to provide additional free space around the figure.
     *
     * @param figure The 3D group to be focused on by the camera.
     *               This group represents the object whose bounds
     *               are used to calculate the necessary adjustments
     *               for the camera's position.
     */
    public void focusFullFigure(Group figure) {
        Bounds bounds = figure.getBoundsInParent();
        double depth = bounds.getDepth() / 2;
        double width = bounds.getWidth() / 2;
        double height = bounds.getHeight() / 2;
        double fovY = this.getFieldOfView();

        double requiredDistance = (Math.max(depth, Math.max(width, height))) / Math.tan(Math.toRadians(fovY / 2));
        requiredDistance *= 1.5; // add some free space to the FOV

        this.resetPositionInZ = -requiredDistance;
        this.resetView();
    }
}
