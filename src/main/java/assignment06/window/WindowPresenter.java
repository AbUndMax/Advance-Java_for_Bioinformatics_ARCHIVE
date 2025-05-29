package assignment06.window;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.PointLight;
import javafx.scene.AmbientLight;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class WindowPresenter {

    private static final PerspectiveCamera camera = new PerspectiveCamera(true);
    // initial view on the tripod is saved as Affine. It is also used as reset position.
    private static final Affine initialTransform = new Affine(
            -0.7071067811865475, 0.7071067811865476, 4.329780281177467E-17, 0.0,
	        0.24184476264797528, 0.24184476264797528, -0.9396926207859084, 0.0,
            -0.6644630243886748, -0.6644630243886747, -0.34202014332566877, 0.0
    );

    public WindowPresenter(WindowController controller) {

        Group contentGroup = setup3DSubPane(controller);
        Group innerGroup = new Group();

        innerGroup.getChildren().add(new Axes(20));
        innerGroup.getChildren().addListener((InvalidationListener) e -> GroupTranslations.centerGroupToItself(innerGroup));

        contentGroup.getChildren().add(innerGroup);
        contentGroup.getTransforms().setAll(initialTransform);

        // set rotation functions
        controller.getRotateUpButton().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(1, 0, 0), -10));
        controller.getRotateUpMenuItem().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(1, 0, 0), -10));
        controller.getRotateDownButton().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(1,0,0), 10));
        controller.getRotateDownMenuItem().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(1,0,0), 10));
        controller.getRotateLeftButton().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(0, 1, 0), 10));
        controller.getRotateLeftMenuItem().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(0, 1, 0), 10));
        controller.getRotateRightButton().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(0,1,0), -10));
        controller.getRotateRightMenuItem().setOnAction(e -> applyGlobalRotation(contentGroup, new Point3D(0,1,0), -10));

        // set zoom functions
        controller.getZoomInButton().setOnAction(e -> camera.setTranslateZ(camera.getTranslateZ() + 10));
        controller.getZoomInMenuItem().setOnAction(e -> camera.setTranslateZ(camera.getTranslateZ() + 10));
        controller.getZoomOutButton().setOnAction(e -> camera.setTranslateZ(camera.getTranslateZ() - 10));
        controller.getZoomOutMenuItem().setOnAction(e -> camera.setTranslateZ(camera.getTranslateZ() - 10));
        controller.getResetButton().setOnAction(e -> {
            camera.setTranslateZ(-100);
            contentGroup.getTransforms().setAll(initialTransform);
        });
        controller.getResetMenuItem().setOnAction(e -> {
            camera.setTranslateZ(-100);
            contentGroup.getTransforms().setAll(initialTransform);
        });

        // open obj files
        controller.getOpenMenuItem().setOnAction(e -> open(contentGroup));

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

    public static void open(Group contentGroup) {
        OpenOBJ.open(contentGroup);
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
        camera.setTranslateZ(-100); // back away from the origin ...
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

        return contentGroup;
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
}
