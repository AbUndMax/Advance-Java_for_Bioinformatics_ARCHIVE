package explorer;

import explorer.window.WindowView;

import explorer.window.presenter.MainViewPresenter;
import explorer.window.presenter.SelectionViewPresenter;
import explorer.window.presenter.VisualizationViewPresenter;
import javafx.application.Application;
import javafx.stage.Stage;

public class AnatomyExplorer extends Application {

    public void start(Stage primaryStage) throws Exception {
        var view = new WindowView();

        // using presenter classes for each view respectively for better clarity and smaller individual presenter classes
        new MainViewPresenter(view.getMainController());
        new SelectionViewPresenter(view.getSelectionController());
        new VisualizationViewPresenter(view.getVisualizationController());

        primaryStage.setTitle("Anatomy Explorer");
        primaryStage.setScene(new javafx.scene.Scene(view.getRoot(), 1000, 700));
        primaryStage.setMinWidth(820);
        primaryStage.setMinHeight(650);
        primaryStage.show();
    }
}
