package assignment05;

import assignment05.window.WindowPresenter;
import assignment05.window.WindowView;
import javafx.application.Application;
import javafx.stage.Stage;

public class ObjectViewer extends Application {

    public void start(Stage primaryStage) throws Exception {
        var view = new WindowView();
        var presenter = new WindowPresenter(view.getController());

        primaryStage.setTitle("Object Viewer");
        primaryStage.setScene(new javafx.scene.Scene(view.getRoot(), 800, 600));
        primaryStage.show();
    }
}
