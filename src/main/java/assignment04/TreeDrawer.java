package assignment04;

import assignment04.model.Model;
import assignment04.window.WindowPresenter;
import assignment04.window.WindowView;
import javafx.application.Application;
import javafx.stage.Stage;


public class TreeDrawer extends Application {

    public void start(Stage primaryStage) throws Exception {
        var view = new WindowView();
        var model = new Model();
        var presenter = new WindowPresenter(primaryStage, view.getController(), model);

        primaryStage.setTitle("Tree Drawer");
        primaryStage.setScene(new javafx.scene.Scene(view.getRoot(), 800, 600));
        primaryStage.show();
    }
}
