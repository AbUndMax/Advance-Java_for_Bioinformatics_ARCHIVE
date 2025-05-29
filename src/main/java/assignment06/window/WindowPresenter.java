package assignment06.window;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.geometry.Bounds;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.PointLight;
import javafx.scene.AmbientLight;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class WindowPresenter {

    private static final PerspectiveCamera camera = new PerspectiveCamera(true);
    // initial view on the tripod is saved as Affine. It is also used as reset position.
    private static final Affine initialTransform = new Affine(
            1.0, 0.0, 0.0, 0.0,
	        0.0, 0.0, -1.0, 0.0,
            0.0, 1, 0.0, 0.0
    );
    private static final int initialCameraPosition = (-1500);
    private static final int zoomStep = 50;
    private static final int rotationStep = 10;

    public WindowPresenter(WindowController controller) {

        Group contentGroup = setup3DSubPane(controller);
        Group innerGroup = new Group();

        //add self centering property
        innerGroup.getChildren().addListener((InvalidationListener) e -> centerGroupToItself(innerGroup));
        innerGroup.getChildren().add(new Axes(20));

        contentGroup.getChildren().add(innerGroup);
        contentGroup.getTransforms().setAll(initialTransform);

        // set rotation functions
        controller.getRotateUpButton().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(1, 0, 0), -rotationStep));
        controller.getRotateUpMenuItem().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(1, 0, 0), -rotationStep));
        controller.getRotateDownButton().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(1,0,0), rotationStep));
        controller.getRotateDownMenuItem().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(1,0,0), rotationStep));
        controller.getRotateLeftButton().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(0, 1, 0), rotationStep));
        controller.getRotateLeftMenuItem().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(0, 1, 0), rotationStep));
        controller.getRotateRightButton().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(0,1,0), -rotationStep));
        controller.getRotateRightMenuItem().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(0,1,0), -rotationStep));

        // set zoom functions
        controller.getZoomInButton().setOnAction(e -> camera.setTranslateZ(camera.getTranslateZ() + zoomStep));
        controller.getZoomInMenuItem().setOnAction(e -> camera.setTranslateZ(camera.getTranslateZ() + zoomStep));
        controller.getZoomOutButton().setOnAction(e -> camera.setTranslateZ(camera.getTranslateZ() - zoomStep));
        controller.getZoomOutMenuItem().setOnAction(e -> camera.setTranslateZ(camera.getTranslateZ() - zoomStep));
        controller.getResetButton().setOnAction(e -> resetView(contentGroup));
        controller.getResetMenuItem().setOnAction(e -> resetView(contentGroup));

        // open obj files
        controller.getOpenMenuItem().setOnAction(e -> OpenOBJ.open(innerGroup));
        controller.getClearMenuItem().setOnAction(e -> clear(innerGroup));

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
        camera.setFarClip(10000);
        camera.setNearClip(0.1);
        camera.setTranslateZ(initialCameraPosition); // back away from the origin ...
        camera.setTranslateY(-10); // move the camera a little bit up to center the objects
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
        camera.setTranslateZ(initialCameraPosition);
        contentGroup.getTransforms().setAll(initialTransform);
    }

    /**
     * Applies a global rotation to the given content group around a specified axis by a given angle.
     * If the group does not already have transforms, the rotation transform is added.
     * If the group has existing transforms, the rotation is concatenated to the current transforms.
     *
     * @param contentGroup The group of 3D objects to which the rotation will be applied.
     * @param axis The axis of rotation specified as a {@code Point3D}.
     * @param angle The angle of rotation in degrees.
     */
    private static void applyGlobalRotation(Group contentGroup, Point3D axis, double angle) {
        var rotate = new Rotate(angle, axis);
        if (contentGroup.getTransforms().isEmpty()) {
            contentGroup.getTransforms().add(rotate);
        } else {
            var currentTransform = contentGroup.getTransforms().get(0);
            currentTransform = rotate.createConcatenation(currentTransform);
            contentGroup.getTransforms().setAll(currentTransform);
        }
    }

    /**
     * Centers the specified 3D group to its own local bounding box.
     * This method calculates the center point of the group's local bounds
     * and applies a translation to shift the group so that its center aligns with the origin.
     *
     * @param group The 3D group to be centered relative to its local coordinate system.
     */
    public static void centerGroupToItself(Group group) {
        Bounds bounds = group.getBoundsInLocal();
        double X = (bounds.getMinX() + bounds.getMaxX()) / 2;
        double Y = (bounds.getMinY() + bounds.getMaxY()) / 2;
        double Z = (bounds.getMinZ() + bounds.getMaxZ()) / 2;
        group.getTransforms().setAll(new Translate(-X, -Y, -Z));
    }

    private static void zoomScrolling(ScrollEvent event) {
        double deltaY = event.getDeltaY();
        camera.setTranslateZ(camera.getTranslateZ() + deltaY);
    }

    private static void clear(Group innergroup) {
        innergroup.getChildren().clear();
    }
}
