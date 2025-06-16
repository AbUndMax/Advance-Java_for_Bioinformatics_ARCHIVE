package explorer.window.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;

public class SelectionController {
    @FXML
    private Button centerDivider1;

    @FXML
    private Button centerDivider2;

    @FXML
    private Button expandIsAView;

    @FXML
    private Button expandPartOfView;

    @FXML
    private SplitPane selectionSplitPane;

    @FXML
    private SplitPane selectionListSplitPane;

    @FXML
    private ToggleButton selectionListToggle;

    @FXML
    private BorderPane selectionListPane;

    @FXML
    public void initialize() {
        setDividerControls();
        setSelectionListControls();
    }

    /**
     * sets the corresponding actions for the divider buttons between the two treeViews
     */
    private void setDividerControls() {
        centerDivider1.setOnAction(e -> selectionSplitPane.setDividerPositions(0.5));
        centerDivider2.setOnAction(e -> selectionSplitPane.setDividerPositions(0.5));

        expandIsAView.setOnAction(e -> selectionSplitPane.setDividerPositions(0));
        expandPartOfView.setOnAction(e -> selectionSplitPane.setDividerPositions(1));
    }

    /**
     * sets the correct toggle action for the selectionList panel
     */
    private void setSelectionListControls() {
        selectionListSplitPane.getItems().remove(selectionListPane);

        // listening to the toggle button
        selectionListToggle.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            ObservableList<Node> items = selectionListSplitPane.getItems();

            if (isSelected && !items.contains(selectionListPane)) {
                // if button is selected, add the selectionPane and set the divider to the most right side
                items.add(selectionListPane);
                selectionListSplitPane.setDividerPositions(1);
            } else if (!isSelected && items.contains(selectionListPane)) {
                // if not selected, remove the selectionListPane
                items.remove(selectionListPane);
            }
        });
    }
}
