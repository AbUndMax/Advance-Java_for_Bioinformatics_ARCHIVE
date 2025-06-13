package assignment03;

import assignment03.model.Model;
import assignment03.window.WindowPresenter;
import assignment03.window.WindowView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author Niklas Gerbes, Luis Reimer
 */
public class WordExplorerMain extends Application {

    public void start(Stage primaryStage) throws Exception {
        var view = new WindowView();
        var model = new Model();
        var presenter = new WindowPresenter(primaryStage, view.getController(), model);

        primaryStage.setTitle("Word Explorer");
        primaryStage.setScene(new javafx.scene.Scene(view.getRoot(), 800, 600));
        primaryStage.show();
    }
}
