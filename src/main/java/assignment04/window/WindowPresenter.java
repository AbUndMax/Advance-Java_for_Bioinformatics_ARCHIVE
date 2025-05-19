package assignment04.window;

import assignment04.model.ANode;
import assignment04.model.Cladogram;
import assignment04.model.Model;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Map;

public class WindowPresenter {

    public WindowPresenter(Stage stage, WindowController controller, Model model) {
        //here we assign actions to the buttons
        controller.getMenuItemClose().setOnAction(event -> Platform.exit());
        //TODO: connect Menu buttons with functions

        controller.getRadioEqualEdge().setOnAction(event -> drawTreeToPane(controller, model));
        controller.getRadioEqualLeaf().setOnAction(event -> drawTreeToPane(controller, model));

        controller.getFitButton().setOnAction(e -> fitButtonHandler(controller, model));
        controller.getExpandButton().setOnAction(e -> drawTreeToPane(controller, model));

        controller.getFilterTextField().textProperty().addListener((obs, oldText, newText) -> {
            filterHandler(controller, model);
        });

        drawTreeToPane(controller, model);

    }

    /**
     * Draws a tree structure onto the designated pane in the user interface.
     * The method retrieves the tree structure from the given model and lays out the tree
     * based on the selected edge layout option (uniform edge length or equal leaf depth).
     * The tree is then rendered as a graphical group and added to the tree pane in the controller.
     *
     * @param controller the WindowController instance managing the UI components,
     *                   including the pane where the tree is displayed
     * @param model      the Model instance containing the tree structure to be displayed
     */
    private static void drawTreeToPane(WindowController controller, Model model) {
        ANode tree = model.getTree();

        boolean state = controller.getRadioEqualEdge().isSelected();
        Map<ANode, Point2D> map = state ? Cladogram.layoutUniformEdgeLength(tree) : Cladogram.layoutEqualLeafDepth(tree);

        Group group = DrawCladogram.apply(tree, map);
        StackPane pane = controller.getTreePane();
        pane.getChildren().clear();
        pane.getChildren().add(group);
    }

    /**
     * Handles the action for the "Fit" button in the user interface. The method adjusts
     * the layout of a tree structure based on the selected edge layout option (uniform
     * edge length or equal leaf depth) and redraws the tree on the associated pane.
     *
     * @param controller the WindowController instance that provides access to UI components
     *                   such as the radio buttons, scroll pane, and tree pane
     * @param model      the Model instance containing the root of the tree structure
     */
    private static void fitButtonHandler(WindowController controller, Model model) {

        ANode tree = model.getTree();

        // if there are letters in the filter field, apply filter on the tree
        if (!controller.getFilterTextField().getText().isEmpty()) {
            String filter = controller.getFilterTextField().getText();
            tree = tree.applyFilter(filter);
        }

        boolean state = controller.getRadioEqualEdge().isSelected();
        Map<ANode, Point2D> map = state ? Cladogram.layoutUniformEdgeLength(tree): Cladogram.layoutEqualLeafDepth(tree);

        double width = controller.getScrollPane().getViewportBounds().getWidth();
        double height = controller.getScrollPane().getViewportBounds().getHeight();
        // find largest label:
        double fontSize = DrawCladogram.calculateFontSize(height, tree.getNumberOfLeaves());
        String longestString = DrawCladogram.calculateLongestStringInMap(map);
        System.out.println(longestString);
        Text longestLeaveText = new Text(longestString + " ");
        longestLeaveText.setFont(Font.font("Arial", fontSize));

        double longestStringSpace = longestLeaveText.getLayoutBounds().getWidth();
        double longestStringSpaceHeight = longestLeaveText.getLayoutBounds().getHeight();
        // the label size is not contained in computing the edge lengths.
        // so to account for the label size we substract the longestlabel size from the available width to mimic the
        // free space to draw the edges on!
        width = width - longestStringSpace;
        height = height - longestStringSpaceHeight;

        controller.getTreePane().getChildren().clear();
        controller.getTreePane().getChildren().add(
                DrawCladogram.apply(tree, map, width, height));
    }

    /**
     * Handles the filtering of a tree structure based on the text input provided in the filter text field of the
     * user interface. A copy of the tree structure is created to preserve the original, then filtered based on the
     * input text, and finally rendered onto the tree pane.
     *
     * @param controller the WindowController instance managing the user interface components, including the
     *                   filter text field and tree pane
     * @param model      the Model instance containing the root of the tree structure to be filtered and displayed
     */
    private static void filterHandler(WindowController controller, Model model) {
        String filter = controller.getFilterTextField().getText();
        ANode tree = model.getTree();
        tree.applyFilter(filter);
        drawTreeToPane(controller, model);
    }
}
