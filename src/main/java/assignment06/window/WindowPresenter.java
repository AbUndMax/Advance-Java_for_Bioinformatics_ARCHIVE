package assignment06.window;

import javafx.application.Platform;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

public class WindowPresenter {

    private static final MyCamera camera = new MyCamera();
    // initial view on the tripod is saved as Affine. It is also used as reset position.
    private static final Affine initialTransform = new Affine(
            1.0, 0.0, 0.0, 0.0,
	        0.0, 0.0, -1.0, 0.0,
            0.0, 1, 0.0, 0.0
    );
    private static final int zoomStep = 50;
    private static final int rotationStep = 10;


    /**
     * Constructs a WindowPresenter object to control and manage the interaction
     * with the 3D scene and related UI components within the application.
     * This class sets up interactive functionalities for 3D viewing, rotation, zooming,
     * and file handling, along with cleaning up and closing the application.
     *
     * @param controller The WindowController instance that contains the UI components
     *                   and actions for interacting with the 3D viewer.
     */
    public WindowPresenter(WindowController controller) {

        Group contentGroup = setup3DSubPane(controller);
        Group innerGroup = new Group();

        //innerGroup.getChildren().add(new Axes(20));
        addAxes(innerGroup);

        contentGroup.getChildren().add(innerGroup);
        contentGroup.getTransforms().setAll(initialTransform);

        // set rotation functions
        controller.getRotateUpButton().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(1, 0, 0), -rotationStep));
        controller.getRotateUpMenuItem().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(1, 0, 0), -rotationStep));
        controller.getRotateDownButton().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(1,0,0), rotationStep));
        controller.getRotateDownMenuItem().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(1,0,0), rotationStep));
        controller.getRotateLeftButton().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(0, 1, 0), rotationStep));
        controller.getRotateLeftMenuItem().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(0, 1, 0), rotationStep));
        controller.getRotateRightButton().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(0,1, 0), -rotationStep));
        controller.getRotateRightMenuItem().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(0,1, 0), -rotationStep));

        // set zoom functions
        controller.getZoomInButton().setOnAction(e -> camera.setTranslateZ(camera.getTranslateZ() + zoomStep));
        controller.getZoomInMenuItem().setOnAction(e -> camera.setTranslateZ(camera.getTranslateZ() + zoomStep));
        controller.getZoomOutButton().setOnAction(e -> camera.setTranslateZ(camera.getTranslateZ() - zoomStep));
        controller.getZoomOutMenuItem().setOnAction(e -> camera.setTranslateZ(camera.getTranslateZ() - zoomStep));
        controller.getResetButton().setOnAction(e -> resetView(contentGroup));
        controller.getResetMenuItem().setOnAction(e -> resetView(contentGroup));

        // open obj files
        controller.getOpenMenuItem().setOnAction(e -> OpenOBJ.open(innerGroup, camera));
        controller.getClearMenuItem().setOnAction(e -> clear(innerGroup));
        controller.getClearButton().setOnAction(e -> clear(innerGroup));

        // set close function
        controller.getCloseButton().setOnAction(e -> Platform.exit());
        controller.getCloseMenuItem().setOnAction(e -> Platform.exit());

        // about
        controller.getAboutMenuItem().setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("3D .obj viewer    ＼(◎o◎)／");
            alert.setContentText("Implemented by Luis Reimer & Niklas Gerbes! \n" +
                                         "This tool is part of the summer term course \n" +
                                         "\"Advanced Java for Bioinformatics\" \n" +
                                         "2025 U. Tübingen by Prof. D. Huson");
            alert.showAndWait();
        });

        controller.getAddAxesMenuItem().setOnAction(e -> {if (!hasAxes(innerGroup))addAxes(innerGroup);});
        controller.getRmAxesMenuItem().setOnAction(e -> rmAxes(innerGroup));
    }

    /**
     * Sets up a 3D sub-pane for rendering 3D objects within the application.
     * Creates and configures a 3D scene with lighting, camera, and background color.
     * Adds the configured subscene to the application's 3D drawing pane.
     *
     * @param controller The WindowController instance containing the 3D drawing pane
     *                   and other UI components associated with the application.
     * @return A Group representing the root content group of the 3D scene,
     *         which can be further used for adding 3D objects to the scene.
     */
    private static Group setup3DSubPane(WindowController controller) {
        Pane drawPane = controller.getThreeDpane();

        Group contentGroup = new Group(); // gets the objects to draw
        Group root3d = new Group(contentGroup);

        var subScene = new SubScene(root3d, 600, 600, true, SceneAntialiasing.BALANCED);
        subScene.widthProperty().bind(drawPane.widthProperty());
        subScene.heightProperty().bind(drawPane.heightProperty());
        // make suScene background lightgrey
        subScene.setFill(Color.LIGHTGRAY);

        // add camera
        subScene.setCamera(camera);

        // Add PointLight
        PointLight pointLight = new PointLight(Color.DARKGREY);
        pointLight.setTranslateX(100);
        pointLight.setTranslateY(-100);
        pointLight.setTranslateZ(-100);

        // Add AmbientLight
        AmbientLight ambientLight = new AmbientLight(Color.rgb(180, 180, 180));

        // Add lights to the root3d group
        root3d.getChildren().addAll(pointLight, ambientLight);

        drawPane.getChildren().add(subScene);

        // Mouse interactions:
        MouseRotate3D.setup(drawPane, contentGroup);
        //add zoom functionality
        //drawPane.setOnScroll(WindowPresenter::zoomScrolling);
        drawPane.setOnScroll(WindowPresenter::zoomScrolling);

        return contentGroup;
    }

    /**
     * Resets the view of the 3D scene to its initial state by adjusting
     * the camera position and resetting the transformations of the content group.
     *
     * @param contentGroup The group of 3D content whose transformations will be reset.
     */
    private static void resetView(Group contentGroup) {
        camera.resetView();
        contentGroup.getTransforms().setAll(initialTransform);
    }

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
     */
    protected static void applyGlobalRotation(Group contentGroup, Point3D axis, double angle) {
        var currentTransform = contentGroup.getTransforms().getFirst();
        var rotate = new Rotate(angle, axis);
        currentTransform = rotate.createConcatenation(currentTransform);
        contentGroup.getTransforms().setAll(currentTransform);
    }

    /**
     * Handles zoom functionality for 3D navigation in response to scroll events.
     * Depending on user input, adjusts either the Z-axis or Y-axis translation
     * of the camera to zoom and pan within the 3D scene.
     *
     * @param event The scroll event containing details such as scroll direction,
     *              scroll distance, and modifier keys that are pressed.
     */
    private static void zoomScrolling(ScrollEvent event) {
        double deltaY = event.getDeltaY();
        if (event.isControlDown()) camera.setTranslateY(camera.getTranslateY() + deltaY);
        else camera.setTranslateZ(camera.getTranslateZ() + deltaY);
    }

    /**
     * Adds a set of 3D {@code Axes} to the specified group. The axes are representing a tripod
     *
     * @param innergroup The group to which the axes will be added. This group
     *                   will visually represent the 3D coordinate system with
     *                   the added axes for orientation.
     */
    private static void addAxes(Group innergroup) {
        innergroup.getChildren().add(new Axes(20));
    }

    /**
     * Removes all instances of {@code Axes} from the specified {@code Group}.
     * This method iterates through the child nodes of the group and filters out
     * any nodes that are instances of the {@code Axes} class, effectively clearing
     * any 3D axes previously added to the group.
     *
     * @param innergroup The {@code Group} from which all instances of {@code Axes}
     *                   will be removed.
     */
    private static void rmAxes(Group innergroup) {
        innergroup.getChildren().removeIf(node -> node instanceof Axes);
    }

    /**
     * Checks if the specified {@code Group} contains any instances of the {@code Axes} class.
     * Iterates through the child nodes of the given group and evaluates whether
     * any of them are an instance of {@code Axes}.
     *
     * @param innergroup The {@code Group} to be inspected for the presence of {@code Axes} instances.
     * @return {@code true} if the group contains one or more instances of {@code Axes}, otherwise {@code false}.
     */
    private static boolean hasAxes(Group innergroup) {
        for (Node n : innergroup.getChildren()) {
            if (n instanceof Axes) return true;
        }
        return false;
    }

    /**
     * Clears all child nodes from the specified 3D {@code Group}.
     * This method removes all objects present in the group, effectively resetting it to an empty state.
     *
     * @param innergroup The {@code Group} whose child nodes will be removed.
     *                   Modifications to this group will affect its visual content in the scene.
     */
    private static void clear(Group innergroup) {
        innergroup.getChildren().clear();
    }
}
