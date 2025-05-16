package assignment04.window;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

public class WindowController {

    @FXML //die anchorpane innerhalb der scrollpane
    private StackPane treePane;

    @FXML
    private ToggleGroup EdgeLength;

    @FXML
    private Label botLabel;

    @FXML
    private Button expandButton;

    @FXML
    private Button fitButton;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem menuItemClose;

    @FXML
    private MenuItem menuItemExpand;

    @FXML
    private MenuItem menuItemFit;

    @FXML
    private MenuItem menuItemFullscreen;

    @FXML
    private MenuItem menuItemNewick;

    @FXML
    private RadioButton radioEqualEdge;

    @FXML
    private RadioButton radioEqualLeaf;

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

    public MenuItem getMenuItemNewick() {return menuItemNewick;}
    public MenuItem getMenuItemClose() {return menuItemClose;}
    public MenuItem getMenuItemFit() {return menuItemFit;}
    public MenuItem getMenuItemFullscreen() {return menuItemFullscreen;}
    public MenuItem getMenuItemExpand() {return menuItemExpand;}
    public StackPane getTreePane() {return treePane;}

}
