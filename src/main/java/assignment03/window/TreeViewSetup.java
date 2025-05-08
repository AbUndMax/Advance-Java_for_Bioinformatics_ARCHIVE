package assignment03.window;

import assignment03.model.ANode;
import assignment03.model.Model;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class TreeViewSetup {

    public static void setupTree(TreeView<ANode> treeView, Model model) {
        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TreeItem<ANode> root = createTreeItemsRec(model.getPartOfRoot());
        treeView.setRoot(root);
    }

    /**
     * Helper Function to recursively create TreeItems to populate the TreeView.
     */
    private static TreeItem<ANode> createTreeItemsRec(ANode treeRoot) {
        TreeItem<ANode> item = new TreeItem<>(treeRoot);
        if (!treeRoot.children().isEmpty()) {
            for (ANode child : treeRoot.children()) {
                item.getChildren().add(createTreeItemsRec(child));
            }
        }
        return item;
    }
}
