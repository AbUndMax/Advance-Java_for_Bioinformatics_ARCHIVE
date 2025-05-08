package assignment03.window;

import assignment03.model.ANode;
import assignment03.model.Model;
import assignment03.model.WordCloudItem;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;

public class WindowPresenter {
    public WindowPresenter(Stage stage, WindowController controller, Model model) {
        //here we assign actions to the buttons
        controller.getMenuItemClose().setOnAction(e -> Platform.exit());

        controller.getMenuItemExpandAll().setOnAction(e -> expandTreeView(controller.getTreeView().getRoot()));
        controller.getMenuItemCollapseAll().setOnAction(e -> collapseTreeView(controller.getTreeView().getRoot()));
        controller.getMenuItemSelectAll().setOnAction(e -> selectAll(controller.getTreeView()));
        controller.getMenuItemSelectNone().setOnAction(e -> selectNone(controller));

        controller.getButtonExpandAll().setOnAction(e -> expandTreeView(controller.getTreeView().getRoot()));
        controller.getButtonCollapseAll().setOnAction(e -> collapseTreeView(controller.getTreeView().getRoot()));
        controller.getButtonSelectAll().setOnAction(e -> selectAll(controller.getTreeView()));
        controller.getButtonSelectNone().setOnAction(e -> selectNone(controller));

        TreeViewSetup.setupTree(controller.getTreeView(), model);
        controller.getTreeView().getSelectionModel()
                .selectedItemProperty().addListener(e -> updateWordCloud(controller));
    }

    /**
     * Helper function to recursively expand all nodes below the input node
     * @param item from which all nodes should be expanded
     */
    private void expandTreeView(TreeItem<ANode> item) {
        if (item != null) {
            item.setExpanded(true);
            for (TreeItem<ANode> child : item.getChildren()) {
                expandTreeView(child);
            }
        }
    }

    /**
     * Helper function to recursively collapse all nodes below the input node
     * @param item from which all nodes below get collapsed
     */
    private void collapseTreeView(TreeItem<ANode> item) {
        if (item != null) {
            item.setExpanded(false);
            for (TreeItem<ANode> child : item.getChildren()) {
                collapseTreeView(child);
            }
        }
    }

    /**
     * Selects all nodes in the given TreeView, starting from the root node.
     * If the TreeView or its root node is null, the operation is skipped.
     *
     * @param treeView the TreeView containing nodes to be selected
     */
    private void selectAll(TreeView<ANode> treeView) {
        if (treeView == null || treeView.getRoot() == null) return;

        var selectionModel = treeView.getSelectionModel();
        selectionModel.clearSelection(); // optional: vorher leeren

        selectAllRecursive(treeView.getRoot(), selectionModel);
    }

    /**
     * Recursively selects a given TreeItem and all its children in the provided selection model.
     *
     * @param item the TreeItem to start the selection process from, including its sub-items
     * @param selectionModel the MultipleSelectionModel used to manage selected TreeItems
     */
    private void selectAllRecursive(TreeItem<ANode> item, MultipleSelectionModel<TreeItem<ANode>> selectionModel) {
        selectionModel.select(item);
        for (TreeItem<ANode> child : item.getChildren()) {
            selectAllRecursive(child, selectionModel);
        }
    }

    /**
     * Deselects all items in the TreeView of the given WindowController and
     * clears the contents of the FlowPane associated with the controller.
     * If the TreeView or its root node is null, the operation is skipped.
     *
     * @param controller the WindowController providing access to the TreeView
     *                   and FlowPane to be updated
     */
    private void selectNone(WindowController controller) {
        TreeView<ANode> treeView = controller.getTreeView();
        controller.getFlowPane().getChildren().clear();

        if (treeView == null || treeView.getRoot() == null) return;
        var selectionModel = treeView.getSelectionModel();
        selectionModel.clearSelection();
    }

    /**
     * Updates the word cloud display in the provided controller's FlowPane based on
     * the selected items in the associated TreeView. Each selected item's name is added
     * to the word cloud, with the relative font size determined by the word's frequency
     * in the selection.
     *
     * @param controller the WindowController containing the FlowPane and TreeView data sources
     */
    private void updateWordCloud(WindowController controller) {
        FlowPane flowPane = controller.getFlowPane();
        flowPane.getChildren().clear();

        //get currently selected items in the treeView
        ObservableList<TreeItem<ANode>> selectedItems = controller.getTreeView().getSelectionModel().getSelectedItems();
        ArrayList<String> words = new ArrayList<>(selectedItems.size()*2);
        //for every selected item, get the value (ANode), get its name, split by \s and add all to words.
        for (TreeItem<ANode> treeItem : selectedItems) {
            words.addAll(Arrays.asList(treeItem.getValue().name().split(" ")));
        }
        //from the list of words, compute the list of WordCloudItems.
        ArrayList<WordCloudItem> wordCloudItems = WordCloudItem.computeItems(words);

        //add all WordCloudItems as Labels to the flowPane; in reverse order so the largest is on top.
        for (int i = wordCloudItems.size()-1; i >= 0; i--) {
            Label word = new Label(wordCloudItems.get(i).word());
            word.setStyle("-fx-font-size: " + wordCloudItems.get(i).relHeight() * 64 + "px;");
            flowPane.getChildren().add(word);
        }
    }


}
