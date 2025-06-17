package explorer.window.controller;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;

public class MainViewController {

    @FXML
    private SplitPane mainSplitPane;

    @FXML
    private MenuItem menuButtonAbout;

    @FXML
    private MenuItem menuButtonClose;

    @FXML
    private MenuItem menuButtonExpandIsA;

    @FXML
    private MenuItem menuButtonExpandPartOf;

    @FXML
    private MenuItem menuButtonResetPosition;

    @FXML
    private MenuItem menuButtonResetSelection;

    @FXML
    private MenuItem menuButtonRotateDown;

    @FXML
    private MenuItem menuButtonRotateLeft;

    @FXML
    private MenuItem menuButtonRotateRight;

    @FXML
    private MenuItem menuButtonRotateUp;

    @FXML
    private MenuItem menuButtonShowSelectionList;

    @FXML
    private MenuItem menuButtonZoomIn;

    @FXML
    private MenuItem menuButtonZoomOut;

    public SplitPane getMainSplitPane() {
        return mainSplitPane;
    }

    public MenuItem getMenuButtonAbout() {
        return menuButtonAbout;
    }

    public MenuItem getMenuButtonClose() {
        return menuButtonClose;
    }

    public MenuItem getMenuButtonExpandIsA() {
        return menuButtonExpandIsA;
    }

    public MenuItem getMenuButtonExpandPartOf() {
        return menuButtonExpandPartOf;
    }

    public MenuItem getMenuButtonResetPosition() {
        return menuButtonResetPosition;
    }

    public MenuItem getMenuButtonResetSelection() {
        return menuButtonResetSelection;
    }

    public MenuItem getMenuButtonRotateDown() {
        return menuButtonRotateDown;
    }

    public MenuItem getMenuButtonRotateLeft() {
        return menuButtonRotateLeft;
    }

    public MenuItem getMenuButtonRotateRight() {
        return menuButtonRotateRight;
    }

    public MenuItem getMenuButtonRotateUp() {
        return menuButtonRotateUp;
    }

    public MenuItem getMenuButtonShowSelectionList() {
        return menuButtonShowSelectionList;
    }

    public MenuItem getMenuButtonZoomIn() {
        return menuButtonZoomIn;
    }

    public MenuItem getMenuButtonZoomOut() {
        return menuButtonZoomOut;
    }

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
