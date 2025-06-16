package explorer.window.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;

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
    public void initialize() {
        centerDivider1.setOnAction(e -> selectionSplitPane.setDividerPositions(0.5));
        centerDivider2.setOnAction(e -> selectionSplitPane.setDividerPositions(0.5));

        expandIsAView.setOnAction(e -> selectionSplitPane.setDividerPositions(0));
        expandPartOfView.setOnAction(e -> selectionSplitPane.setDividerPositions(1));
    }
}
