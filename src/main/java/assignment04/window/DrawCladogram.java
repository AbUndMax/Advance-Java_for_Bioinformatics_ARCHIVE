package assignment04.window;

import assignment04.model.ANode;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;

import java.util.Map;

public class DrawCladogram {


    public static Group apply(
            ANode root, Map<ANode, Point2D> nodePointMap, double width, double height)
    {
        Group nodes = new Group();
        Group edges = new Group();
        Group labels = new Group();
        //TODO: use of width and height is not yet implemented

        generateGroupsRec(root, nodes, edges, labels, nodePointMap);

        return new Group(nodes, edges, labels);
    }


    public static void generateGroupsRec(ANode thisNode, Group nodes, Group edges, Group labels, Map<ANode, Point2D> nodePointMap) {

        //add circle to nodes-group
        nodes.getChildren().add(createCircle(nodePointMap.get(thisNode)));

        if (thisNode.children().isEmpty()) {
            //if this is a leaf, add text to labels-group
            labels.getChildren().add(createLabel(thisNode, nodePointMap.get(thisNode)));
            return;
        } else {
            //for every child of this node: add an edge to it and recurse on it
            for (ANode child : thisNode.children()) {
                edges.getChildren().add(createEdge(nodePointMap.get(thisNode), nodePointMap.get(child)));
                generateGroupsRec(child, nodes, edges, labels, nodePointMap);
            }
        }
    }


    /**
     * Below: some helper functions to create the javafx elements of the tree.
     */
    public static Polyline createEdge(Point2D a, Point2D b) {
        return new Polyline(a.getX(), a.getY(), a.getX(), b.getY(), b.getX(), b.getY());
    }
    public static Text createLabel(ANode node, Point2D p) {
        return new Text(p.getX(), p.getY(), node.name());
    }
    public static Circle createCircle(Point2D p, double radius) {
        return new Circle(p.getX(), p.getY(), radius);
    }
    public static Circle createCircle(Point2D p) {
        return createCircle(p, 10);
    }


}
