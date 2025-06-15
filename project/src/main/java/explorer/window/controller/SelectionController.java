package explorer.window.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;

public class SelectionController {
    @FXML
    private Button center_divider_1;

    @FXML
    private Button center_divider_2;

    @FXML
    private Button expand_is_a;

    @FXML
    private Button expand_part_of;

    @FXML
    private SplitPane selection_SplitPane;

    @FXML
    public void initialize() {
        center_divider_1.setOnAction(e -> selection_SplitPane.setDividerPositions(0.5));
        center_divider_2.setOnAction(e -> selection_SplitPane.setDividerPositions(0.5));

        expand_is_a.setOnAction(e -> selection_SplitPane.setDividerPositions(0));
        expand_part_of.setOnAction(e -> selection_SplitPane.setDividerPositions(1));
    }
}
