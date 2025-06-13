package assignment06.window;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class WindowView {
    private final WindowController controller;
    private final Parent root;

    public WindowView() throws IOException {
        URL fxmlUrl = getClass().getResource("/asgmt06_res/Window.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        root = loader.load();
        controller = loader.getController();
    }

    public WindowController getController() {return controller; }
    public Parent getRoot() { return root; }
}
