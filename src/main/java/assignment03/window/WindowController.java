package assignment03.window;

import assignment03.model.ANode;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;

public class WindowController {

    @FXML
    private FlowPane flowPane;

    @FXML
    private Button buttonCollapseAll;

    @FXML
    private Button buttonExpandAll;

    @FXML
    private Button buttonSelectAll;

    @FXML
    private Button buttonSelectNone;

    @FXML
    private MenuItem menuItemClose;

    @FXML
    private MenuItem menuItemCollapseAll;

    @FXML
    private MenuItem menuItemExpandAll;

    @FXML
    private MenuItem menuItemSelectAll;

    @FXML
    private MenuItem menuItemSelectNone;

    @FXML
    private TreeView<ANode> treeView;

    public FlowPane getFlowPane() {
        return flowPane;
    }

    public Button getButtonCollapseAll() {
        return buttonCollapseAll;
    }

    public Button getButtonExpandAll() {
        return buttonExpandAll;
    }

    public Button getButtonSelectAll() {
        return buttonSelectAll;
    }

    public Button getButtonSelectNone() {
        return buttonSelectNone;
    }

    public MenuItem getMenuItemClose() {
        return menuItemClose;
    }

    public MenuItem getMenuItemCollapseAll() {
        return menuItemCollapseAll;
    }

    public MenuItem getMenuItemExpandAll() {
        return menuItemExpandAll;
    }

    public MenuItem getMenuItemSelectAll() {
        return menuItemSelectAll;
    }

    public MenuItem getMenuItemSelectNone() {
        return menuItemSelectNone;
    }

    public TreeView<ANode> getTreeView() {
        return treeView;
    }
}
