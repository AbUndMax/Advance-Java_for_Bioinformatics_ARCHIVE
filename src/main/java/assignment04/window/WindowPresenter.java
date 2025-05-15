package assignment04.window;

import assignment04.model.Model;
import javafx.application.Platform;
import javafx.stage.Stage;

public class WindowPresenter {
    public WindowPresenter(Stage stage, WindowController controller, Model model) {
        //here we assign actions to the buttons
        controller.getMenuItemClose().setOnAction(event -> Platform.exit());

        //TODO
    }
}
