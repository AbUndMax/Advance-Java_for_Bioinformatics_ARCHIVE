package explorer.window.controller;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;

public class MainViewController {

    @FXML
    private SplitPane mainSplitPane;

    /**
     * All controls set in the initializer are just for basic GUI behavior!
     * Nothing related to ANY model or window functionality!
     */
    @FXML
    public void initialize() {
        fixDividerOnResize(mainSplitPane);
    }

    private static void fixDividerOnResize(SplitPane splitPane) {
        splitPane.sceneProperty().addListener((obsScene, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.widthProperty().addListener((obsWidth, oldWidth, newWidth) -> {
                    // Speichere beim ersten Resize den aktuellen Pixelwert des Dividers
                    double fixedPixelPos = splitPane.getDividerPositions()[0] * oldWidth.doubleValue();
                    double newRelativePos = fixedPixelPos / newWidth.doubleValue();
                    splitPane.setDividerPositions(newRelativePos);
                });
            }
        });
    }
}
