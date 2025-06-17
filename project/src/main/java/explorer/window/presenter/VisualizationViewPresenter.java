package explorer.window.presenter;

import explorer.window.controller.VisualizationViewController;
import explorer.window.vistools.Axes;
import explorer.window.vistools.MyCamera;
import explorer.window.vistools.TransformUtils;
import javafx.geometry.Bounds;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;

public class VisualizationViewPresenter {

    // constants copied from assignment06
    private static final MyCamera camera = new MyCamera();
    // initial view on the tripod is saved as Affine. It is also used as reset position.
    private static final Affine initialTransform = new Affine(
            1.0, 0.0, 0.0, 0.0,
            0.0, 0.0, -1.0, 0.0,
            0.0, 1.0, 0.0, 0.0
    );
    private static final int rotationStep = 10;

    public VisualizationViewPresenter(VisualizationViewController visualizationViewController) {
        Group contentGroup = setupVisualisationPane(visualizationViewController);
        contentGroup.getChildren().add(new Axes(20));
        contentGroup.getTransforms().setAll(initialTransform);
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
    private static Group setupVisualisationPane(VisualizationViewController controller) {
        Pane visualizationPane = controller.getVisualizationPane();

        Group contentGroup = new Group(); // gets the objects to draw
        Group root3d = new Group(contentGroup);

        var subScene = new SubScene(root3d, 600, 600, true, SceneAntialiasing.BALANCED);
        subScene.widthProperty().bind(visualizationPane.widthProperty());
        subScene.heightProperty().bind(visualizationPane.heightProperty());
        subScene.setMouseTransparent(true);
        // make subScene background lightgrey
        subScene.setFill(Color.DARKGREY);

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

        visualizationPane.getChildren().add(subScene);

        // setup rotation via mouse:
        TransformUtils.setupMouseRotation(visualizationPane, contentGroup);
        //add zoom functionality via scrolling
        visualizationPane.setOnScroll(camera::zoomAndPanScrolling);

        System.out.println(subScene.getWidth() + " " + subScene.getHeight());



        return contentGroup;
    }
}
