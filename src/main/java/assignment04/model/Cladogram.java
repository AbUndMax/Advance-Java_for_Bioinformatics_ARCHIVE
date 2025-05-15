package assignment04.model;

import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Cladogram {

    private static int noLeavesVisited = 0;
    public int getNoLeavesVisited() {return noLeavesVisited;}

    public static Map<ANode, Point2D> layoutEqualLeafDepth(ANode root) {
        //will be filled
        Map<ANode, Point2D> result = new HashMap<>();
        Consumer<ANode> consumer = new Consumer<ANode>() {
            @Override
            public void accept(ANode node) {
                result.put(node, computeCoordsEqualLeafDepth(node, result));
            }
        };

        postOrderTraversal(root, consumer);

        return result;
    }

    public static Map<ANode, Point2D> layoutUniformEdgeLength(ANode root) {
        //TODO NIKLAS
        Map<ANode, Point2D> result = new HashMap<>();

        return result;
    }

    /**
     * Traverses the tree in a post-order manner, starting from the given node and applies
     * the specified function to each node.
     *
     * @param node the root node of the subtree to be traversed in post-order
     * @param function a Consumer function to be applied to each node during traversal
     */
    public static void postOrderTraversal(ANode node, Consumer<ANode> function) {
        for (ANode child : node.children()) {
            postOrderTraversal(child, function);
        }
        function.accept(node);
    }

    /**
     * Traverses a tree in a pre-order manner, starting from the given node, and applies
     * the specified function to each node.
     *
     * @param node the root node of the subtree to be traversed in pre-order
     * @param function a Consumer function to be applied to each node during traversal
     */
    public static void preOrderTraversal(ANode node, Consumer<ANode> function) {
        function.accept(node);
        for (ANode child : node.children()) {
            preOrderTraversal(child, function);
        }
    }


    public static Point2D computeCoordsEqualLeafDepth(ANode node, Map<ANode, Point2D> map) {
        double x,y;
        x = computeXEqualLeafDepth(node, map);
        if (node.children().isEmpty()) {
            y = noLeavesVisited;
            noLeavesVisited++;
        } else y = computeYEqualLeafDepth(node, map);

        return new Point2D(x,y);
    }

    /**
     * requires all children to be present in the map
     */
    public static double computeXEqualLeafDepth(ANode node, Map<ANode, Point2D> map) {
        double min = 1;
        for (ANode child : node.children()) {
            min = Math.min(min, map.get(child).getX());
        }
        return min-1;
    }

    /**
     * only for not-leaves, else you will regret.
     */
    public static double computeYEqualLeafDepth(ANode node, Map<ANode, Point2D> map) {
        double sum = 0;
        int counter = 0;
        for (ANode child : node.children()) {
            sum += map.get(child).getY();
            counter++;
        }
        return sum/counter;
    }
}
