package assignment06.window;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class ObjIO {

    public static MeshView createMeshWithIMG(TriangleMesh mesh, File image) {
        MeshView meshView = new MeshView(mesh);
        PhongMaterial material = new PhongMaterial();
        material.setSpecularColor(Color.DARKGREY);

        if (image.exists()) {
            //meshView.setCullFace(CullFace.FRONT); strangely caused the cube to deform when rotating.
            material.setDiffuseMap(new Image(image.toURI().toString()));
        } else {
            material.setDiffuseColor(Color.GREEN); //indicating that no img is loaded
        }
        meshView.setMaterial(material);
        return meshView;
    }

    public static void open(Group group, MyCamera camera) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open OBJ files");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("OBJ file", "*.obj"),
                new FileChooser.ExtensionFilter("All files", "*.*")
        );

        List<File> objFiles = fileChooser.showOpenMultipleDialog(new Stage()); // choose OBJ files
        if (objFiles == null || objFiles.isEmpty()) {
            return; // Exit early if no files are selected
        }

        List<MeshView> meshViews = new LinkedList<>();  //will hold MeshViews of the parsed OBJ files

        for (File objFile : objFiles) {
            if (objFile != null) {
                String objPath = objFile.getAbsolutePath();
                File imgFile = new File(objPath.substring(0, objPath.length() - 3) + "png");
                try {
                    //generate mesh from the OBJ file
                    meshViews.add(createMeshWithIMG(ObjParser.load(objPath), imgFile));
                } catch (Exception e) {
                    System.err.println("Failed to parse OBJ file: " + objPath);
                }
            }
        }

        //add meshviews from all OBJ files to the content
        // group.getChildren().clear();
        group.getChildren().addAll(meshViews);

        centerGroupToItself(group);
        camera.focusFullFigure(group);
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
}

