package assignment02;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AnatomyDataExplorer extends Application {

    public static void main(String[] args) {
        launch();
    }

    public void start(Stage stage) {
        BorderPane root = new BorderPane();

        // Top area:
        Button expandButton = new Button("Expand");
        Button collapseButton = new Button("Collapse");
        ToolBar toolBar = new ToolBar(expandButton, collapseButton);
        root.setTop(toolBar);

        // Center area:
        ANode treeRoot = TreeLoader.load(
                "src/main/resources/asgmt02_res/partof_parts_list_e.txt",
                "src/main/resources/asgmt02_res/partof_element_parts.txt",
                "src/main/resources/asgmt02_res/partof_inclusion_relation_list.txt");
        TreeItem<ANode> rootItem = createTreeItemsRec(treeRoot);
        TreeView<ANode> treeView = new TreeView<>(rootItem);
        ListView<String> listView = new ListView<>();
        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldItem, newItem) -> {
            listView.getItems().clear();
            if (newItem != null) {
                ANode selectedNode = newItem.getValue();
                for (String fileID : selectedNode.fileIds()) {
                    listView.getItems().add(fileID);
                }
            }
        });
        SplitPane splitPane = new SplitPane(treeView, listView);
        root.setCenter(splitPane);


        // Bottom area:
        ButtonBar buttonBar = new ButtonBar();
        Button byeButton = new Button("Bye");
        buttonBar.getButtons().add(byeButton);
        root.setBottom(buttonBar);

        // Scene setup:
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Anatomy Data Explorer");
        stage.setScene(scene);
        stage.show();

        // Button actions:
        byeButton.setOnAction(e -> stage.close());
        expandButton.setOnAction(e -> expandTreeView(rootItem));
        collapseButton.setOnAction(e -> collapseTreeView(rootItem));
        collapseButton.disableProperty().bind(rootItem.expandedProperty().not());
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
}
