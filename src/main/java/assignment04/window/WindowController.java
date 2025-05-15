package assignment04.window;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.RadioButton;

public class WindowController {

    @FXML
    private Label botLabel;

    @FXML
    private Button expandButton;

    @FXML
    private Button fitButton;

    @FXML
    private MenuBar menuBar;

    public Label getBotLabel() {
        return botLabel;
    }

    public Button getExpandButton() {
        return expandButton;
    }

    public Button getFitButton() {
        return fitButton;
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    public RadioButton getRadioEqualEdge() {
        return radioEqualEdge;
    }

    public RadioButton getRadioEqualLeaf() {
        return radioEqualLeaf;
    }

    @FXML
    private RadioButton radioEqualEdge;

    @FXML
    private RadioButton radioEqualLeaf;

}
