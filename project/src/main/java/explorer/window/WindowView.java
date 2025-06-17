package explorer.window;

import explorer.window.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class WindowView {
    private final Parent root;
    private final MainViewController mainViewController;
    private final SelectionViewController selectionViewController;
    private final VisualizationViewController visualizationViewController;

    public WindowView() throws IOException {
        URL mainFXML = getClass().getResource("/fxml/MainView.fxml");
        FXMLLoader loader = new FXMLLoader(mainFXML);
        root = loader.load();
        mainViewController = loader.getController();

        selectionViewController = loadController("/fxml/SelectionView.fxml");
        visualizationViewController = loadController("/fxml/VisualizationView.fxml");
    }

    private <T> T loadController(String resourcePath) throws IOException {
        URL resource = getClass().getResource(resourcePath);
        FXMLLoader loader = new FXMLLoader(resource);
        loader.load();
        return loader.getController();
    }

    public Parent getRoot() { return root; }
    public MainViewController getMainController() {return mainViewController; }
    public SelectionViewController getSelectionController() {return selectionViewController; }
    public VisualizationViewController getVisualizationController() {return visualizationViewController; }
}
