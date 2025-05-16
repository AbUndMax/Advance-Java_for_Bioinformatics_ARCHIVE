package assignment04.window;

import assignment04.model.ANode;
import assignment04.model.Cladogram;
import assignment04.model.Model;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.util.Map;

public class WindowPresenter {


    public WindowPresenter(Stage stage, WindowController controller, Model model) {
        //here we assign actions to the buttons
        controller.getMenuItemClose().setOnAction(event -> Platform.exit());

        controller.getFitButton().setOnAction(e -> fitButtonHandler(controller, model));

        controller.getFilterTextField().textProperty().addListener((obs, oldText, newText) -> {
            filterHandler(controller, model);
        });

        //TODO
        drawTreeToPane(model.getPartOfRoot(), controller);

    }

    /**
     * Renders a tree structure onto the tree pane within the user interface. The rendering layout is
     * determined based on the selected edge layout option: either uniform edge length or equal leaf depth.
     *
     * @param tree       the root node of the tree structure to be rendered
     * @param controller the WindowController instance managing the user interface components,
     *                   particularly the tree pane and the toggle selection for layout options
     */
    private static void drawTreeToPane(ANode tree, WindowController controller) {
        boolean state = controller.getRadioEqualEdge().isSelected();
        Map<ANode, Point2D> map = state ? Cladogram.layoutUniformEdgeLength(tree) : Cladogram.layoutEqualLeafDepth(tree);
        Group group = DrawCladogram.apply(tree, map);
        //Group group = DrawCladogram.apply(model.getPartOfRoot(), map, centerWidth, centerHeight);
        //System.out.println("Stage height: " + controller.getTreePane().getHeight() + ", Stage width: " + controller.getTreePane().getWidth());
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

        ANode tree = model.getPartOfRoot();

        if (!controller.getFilterTextField().getText().isEmpty()) {
            String filter = controller.getFilterTextField().getText();
            tree = tree.copyTree().filterTree(filter);
        }

        boolean state = controller.getRadioEqualEdge().isSelected();
        Map<ANode, Point2D> map = state ? Cladogram.layoutUniformEdgeLength(tree): Cladogram.layoutEqualLeafDepth(tree);

        double w2 = controller.getScrollPane().getViewportBounds().getWidth();
        double h2 = controller.getScrollPane().getViewportBounds().getHeight();

        controller.getTreePane().getChildren().clear();
        controller.getTreePane().getChildren().add(
                DrawCladogram.apply(tree, map, h2, w2));
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
        ANode filteredTree = model.getPartOfRoot().copyTree().filterTree(filter);
        drawTreeToPane(filteredTree, controller);
    }

    //
    //public static void fitFun(WindowController controller) {
      //  Group group = DrawCladogram.apply();}

}
