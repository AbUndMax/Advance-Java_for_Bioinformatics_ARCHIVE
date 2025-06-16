package explorer.window.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

public class VisualizationController {
    @FXML
    private ChoiceBox<String> searchChoice;

    @FXML
    public void initialize() {
        searchChoice.setValue("3D viewer");
    }
}
