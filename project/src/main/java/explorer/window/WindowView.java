package explorer.window;

import explorer.window.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class WindowView {
    private final Parent root;
    private final MainController mainController;
    private final SelectionController selectionController;
    private final VisualizationController visualizationController;

    public WindowView() throws IOException {
        URL mainFXML = getClass().getResource("/fxml/Main.fxml");
        FXMLLoader loader = new FXMLLoader(mainFXML);
        root = loader.load();
        mainController = loader.getController();

        selectionController = loadController("/fxml/Selection.fxml");
        visualizationController = loadController("/fxml/Visualization.fxml");
    }

    private <T> T loadController(String resourcePath) throws IOException {
        URL resource = getClass().getResource(resourcePath);
        FXMLLoader loader = new FXMLLoader(resource);
        loader.load();
        return loader.getController();
    }

    public Parent getRoot() { return root; }
    public MainController getMainController() {return mainController; }
    public SelectionController getSelectionController() {return selectionController; }
    public VisualizationController getVisualizationController() {return visualizationController; }
}
