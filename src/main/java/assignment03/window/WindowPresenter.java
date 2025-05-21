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
        // here we assign actions to the buttons
        controller.getMenuItemClose().setOnAction(e -> Platform.exit());

        controller.getMenuItemExpandAll().setOnAction(e -> expandTreeView(controller));
        controller.getMenuItemCollapseAll().setOnAction(e -> collapseTreeView(controller));
        controller.getMenuItemSelectAll().setOnAction(e -> selectAll(controller.getTreeView()));
        controller.getMenuItemSelectNone().setOnAction(e -> selectNone(controller));

        controller.getButtonExpandAll().setOnAction(e -> expandTreeView(controller));
        controller.getButtonCollapseAll().setOnAction(e -> collapseTreeView(controller));
        controller.getButtonSelectAll().setOnAction(e -> selectAll(controller.getTreeView()));
        controller.getButtonSelectNone().setOnAction(e -> selectNone(controller));

        TreeViewSetup.setupTree(controller.getTreeView(), model);
        controller.getTreeView().getSelectionModel()
                .selectedItemProperty().addListener(e -> updateWordCloud(controller));
    }

    /**
     * Expands the TreeView by opening all nodes either completely or based on the selected nodes.
     * If no nodes are selected in the TreeView, all nodes starting from the root are expanded.
     * Otherwise, only the subtrees of the selected nodes are expanded.
     * After expansion, selection and word cloud are cleared.
     *
     * @param controller the WindowController providing access to the TreeView and FlowPane
     */
    private void expandTreeView(WindowController controller) {
        TreeView<ANode> treeView = controller.getTreeView();

        ObservableList<TreeItem<ANode>> selectedNodes = treeView.getSelectionModel().getSelectedItems();

        if (selectedNodes.isEmpty()) {
            expandAllBelowGivenNode(treeView.getRoot());
        } else {
            for (TreeItem<ANode> selectedNode : selectedNodes) {
                expandAllBelowGivenNode(selectedNode);
            }
        }

        // Clear selection and word cloud
        treeView.getSelectionModel().clearSelection();
        controller.getFlowPane().getChildren().clear();
    }

    /**
     * Expands the given TreeItem along with all of its child items recursively.
     * If the provided TreeItem is null, the method execution is skipped.
     *
     * @param item the TreeItem to be expanded, along with all its descendants
     */
    private void expandAllBelowGivenNode(TreeItem<ANode> item) {
        if (item != null) {
            item.setExpanded(true);
            for (TreeItem<ANode> child : item.getChildren()) {
                expandAllBelowGivenNode(child);
            }
        }
    }

    /**
     * Collapses the TreeView by closing all nodes either completely or based on the selected nodes.
     * If no nodes are selected in the TreeView, all nodes starting from the root are collapsed.
     * Otherwise, only the subtrees of the selected nodes are collapsed.
     * After collapsing, selection and word cloud are cleared.
     *
     * @param controller the WindowController providing access to the TreeView and FlowPane
     */
    private void collapseTreeView(WindowController controller) {
        TreeView<ANode> treeView = controller.getTreeView();

        ObservableList<TreeItem<ANode>> selectedNodes = treeView.getSelectionModel().getSelectedItems();

        if (selectedNodes.isEmpty()) {
            collapseAllNodesUptToGivenNode(treeView.getRoot());
        } else {
            for (TreeItem<ANode> selectedNode : selectedNodes) {
                collapseAllNodesUptToGivenNode(selectedNode);
            }
        }

        // Clear selection and word cloud
        treeView.getSelectionModel().clearSelection();
        controller.getFlowPane().getChildren().clear();
    }

    /**
     * Helper function to recursively collapse all nodes below the input node
     * @param item from which all nodes below get collapsed
     */
    private void collapseAllNodesUptToGivenNode(TreeItem<ANode> item) {
        if (item != null) {
            item.setExpanded(false);
            for (TreeItem<ANode> child : item.getChildren()) {
                collapseAllNodesUptToGivenNode(child);
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

        ObservableList<TreeItem<ANode>> selectedNodes = selectionModel.getSelectedItems();

        if (selectedNodes.isEmpty()) {
            selectAllRecursive(treeView.getRoot(), selectionModel);
        } else {
            for (TreeItem<ANode> selectedNode : selectedNodes) {
                selectAllRecursive(selectedNode, selectionModel);
            }
        }
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

        // Get currently selected items in the treeView
        ObservableList<TreeItem<ANode>> selectedItems = controller.getTreeView().getSelectionModel().getSelectedItems();
        ArrayList<String> words = new ArrayList<>(selectedItems.size() * 2);

        // Extract words from selected items
        for (TreeItem<ANode> treeItem : selectedItems) {
            words.addAll(Arrays.asList(treeItem.getValue().name().split(" ")));
        }

        // Compute word cloud items
        ArrayList<WordCloudItem> wordCloudItems = WordCloudItem.computeItems(words);

        // Build label list in reverse order for correct visual stacking
        ArrayList<Label> labels = new ArrayList<>(wordCloudItems.size());
        for (int i = wordCloudItems.size() - 1; i >= 0; i--) {
            WordCloudItem item = wordCloudItems.get(i);
            Label label = new Label(item.word());
            label.setStyle("-fx-font-size: " + (int)(item.relHeight() * 64) + "px;");
            labels.add(label);
        }

        // Add all labels at once to reduce layout recalculations
        flowPane.getChildren().setAll(labels);
    }
}
