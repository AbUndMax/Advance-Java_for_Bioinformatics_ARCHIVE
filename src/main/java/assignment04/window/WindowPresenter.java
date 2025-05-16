package assignment04.window;

import assignment04.model.ANode;
import assignment04.model.Cladogram;
import assignment04.model.Model;
import assignment04.model.TreeLoader;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.util.Map;

public class WindowPresenter {


    public WindowPresenter(Stage stage, WindowController controller, Model model) {
        //here we assign actions to the buttons
        controller.getMenuItemClose().setOnAction(event -> Platform.exit());

        controller.getFitButton().setOnAction(e -> {
            boolean state = controller.getRadioEqualEdge().isSelected();
            Map<ANode, Point2D> map = state ? Cladogram.layoutUniformEdgeLength(model.getPartOfRoot()): Cladogram.layoutEqualLeafDepth(model.getPartOfRoot());

            Node center = controller.getInnerBorderPane().getCenter();
            Bounds centerBounds = center.getLayoutBounds();
            double Width = centerBounds.getWidth();
            double Height = centerBounds.getHeight();
            //alternative methode: funktioniert auch nicht ganz :)
            double w2 = controller.getScrollPane().getViewportBounds().getWidth();
            double h2 = controller.getScrollPane().getViewportBounds().getHeight();

            controller.getTreePane().getChildren().clear();
            controller.getTreePane().getChildren().add(
              DrawCladogram.apply(model.getPartOfRoot(), map, h2, w2));
        });

        //TODO
        Map<ANode, Point2D> map = Cladogram.layoutUniformEdgeLength(model.getPartOfRoot());
        Group group = DrawCladogram.apply(model.getPartOfRoot(), map);
        //Group group = DrawCladogram.apply(model.getPartOfRoot(), map, centerWidth, centerHeight);
        //System.out.println("Stage height: " + controller.getTreePane().getHeight() + ", Stage width: " + controller.getTreePane().getWidth());
        controller.getTreePane().getChildren().add(group);


    }

    //
    //public static void fitFun(WindowController controller) {
      //  Group group = DrawCladogram.apply();}

}
