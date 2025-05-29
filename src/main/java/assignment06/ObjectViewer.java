package assignment06;

import assignment06.window.WindowPresenter;
import assignment06.window.WindowView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * The ObjectViewer class serves as the main entry point for launching the
 * Object Viewer application. It initializes the primary stage with a specified
 * scene containing a 3D object viewer interface.
 *
 * This class extends the JavaFX `Application` class, making it responsible for
 * setting up and running the JavaFX application lifecycle.
 *
 * author: Luis Reimer, Niklas Gerbes
 */
public class ObjectViewer extends Application {

    public void start(Stage primaryStage) throws Exception {
        var view = new WindowView();
        var presenter = new WindowPresenter(view.getController());

        primaryStage.setTitle("Object Viewer");
        primaryStage.setScene(new javafx.scene.Scene(view.getRoot(), 800, 600));
        primaryStage.show();
    }
}
