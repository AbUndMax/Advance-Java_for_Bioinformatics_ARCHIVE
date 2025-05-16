package assignment04.window;

import assignment04.model.Model;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class WindowPresenter {
    public WindowPresenter(Stage stage, WindowController controller, Model model) {
        //here we assign actions to the buttons
        controller.getMenuItemClose().setOnAction(event -> Platform.exit());

        //TODO

        Point2D p = new Point2D(1,2);

        Circle circle = new Circle();
        circle.setCenterX(4);
        circle.setCenterX(3);
        circle.setRadius(5);

        controller.getTreePane().getChildren().add(circle);
    }
}
