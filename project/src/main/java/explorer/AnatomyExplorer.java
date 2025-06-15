package explorer;

// import explorer.window.WindowPresenter;
import explorer.window.WindowView;

import javafx.application.Application;
import javafx.stage.Stage;

public class AnatomyExplorer extends Application {

    public void start(Stage primaryStage) throws Exception {
        var view = new WindowView();
        // new WindowPresenter(view.getController());

        primaryStage.setTitle("Anatomy Explorer");
        primaryStage.setScene(new javafx.scene.Scene(view.getRoot(), 1000, 700));
        primaryStage.show();
    }
}
