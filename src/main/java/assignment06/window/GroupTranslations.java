package assignment06.window;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.transform.Translate;

public class GroupTranslations {
    public static void centerGroupToItself(Group group) {
        Bounds bounds = group.getBoundsInLocal();
        double X = (bounds.getMinX() + bounds.getMaxX()) / 2;
        double Y = (bounds.getMinY() + bounds.getMaxY()) / 2;
        double Z = (bounds.getMinZ() + bounds.getMaxZ()) / 2;
        group.getTransforms().setAll(new Translate(-X, -Y, -Z));
    }
}
