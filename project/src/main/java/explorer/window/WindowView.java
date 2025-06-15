package explorer.window;

import explorer.window.controller.MainController;
import explorer.window.controller.SelectionController;
import explorer.window.controller.VisualizationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class WindowView {
    private final Parent root;
    private final MainController mainController;
    private final SelectionController selectionTabController;
    private final VisualizationController visualizationTabController;

    public WindowView() throws IOException {
        URL mainFXML = getClass().getResource("/fxml/Main.fxml");
        FXMLLoader loader = new FXMLLoader(mainFXML);
        root = loader.load();
        mainController = loader.getController();

        selectionTabController = loadController("/fxml/Selection.fxml");
        visualizationTabController = loadController("/fxml/Visualization.fxml");
    }

    private <T> T loadController(String resourcePath) throws IOException {
        URL resource = getClass().getResource(resourcePath);
        FXMLLoader loader = new FXMLLoader(resource);
        loader.load();
        return loader.getController();
    }

    public Parent getRoot() { return root; }
    public MainController getMainController() {return mainController; }
    public SelectionController getSelectionTabController() {return selectionTabController; }
    public VisualizationController getVisualizationTabController() {return visualizationTabController; }
}
