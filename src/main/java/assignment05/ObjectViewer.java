package assignment05;

import assignment05.window.WindowPresenter;
import assignment05.window.WindowView;
import assignment05.model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class ObjectViewer extends Application {

    public void start(Stage primaryStage) throws Exception {
        var view = new WindowView();
        var model = new Model();
        var presenter = new WindowPresenter(view.getController());

        primaryStage.setTitle("Tree Drawer");
        primaryStage.setScene(new javafx.scene.Scene(view.getRoot(), 800, 600));
        primaryStage.show();
    }
}
