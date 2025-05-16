package assignment04.model;

import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Cladogram {

    /**
     * Calculates the layout for a tree structure such that nodes are positioned based on an equal-leaf-depth strategy.
     * Each node is assigned a position in a coordinate space, where the x-coordinate is determined by the structure
     * of its children and the y-coordinate is determined by the number of leaves visited during traversal.
     *
     * @param root the root node of the tree for which the layout should be computed
     * @return a mapping of each node in the tree to its corresponding position as a 2D point
     */
    public static Map<ANode, Point2D> layoutEqualLeafDepth(ANode root) {
        //will be filled
        Map<ANode, Point2D> result = new HashMap<>();

        int[] leavesVisited = {0};
        postOrderTraversal(root, node -> {
            double x,y;
            x = computeXEqualLeafDepth(node, result);
            if (node.children().isEmpty()) {
                y = leavesVisited[0];
                leavesVisited[0]++;
            } else y = computeYEqualLeafDepth(node, result);

            result.put(node, new Point2D(x ,y));
        });

        return result;
    }

    /**
     * Computes a layout for a tree structure such that all edges between nodes have the same length.
     * This method assigns each node a position in a 2D coordinate space based on its relationship
     * with its parent and siblings. The x-coordinates are incremented uniformly while y-coordinates
     * reflect subtree structure with nodes positioned based on averages or leaf counting.
     *
     * @param root the root node of the tree for which the layout is to be computed
     * @return a mapping of each node in the tree to its assigned position as a 2D point,
     *         represented by {@link Point2D}
     */
    public static Map<ANode, Point2D> layoutUniformEdgeLength(ANode root) {
        Map<ANode, Point2D> result = new HashMap<>();

        // First postOrderTraversal for y coord calculation
        // (leaf gets y = incrementing number, inner node = avg. over childs)
        int[] leavesVisited = {0}; // Mutable counter using an array
        postOrderTraversal(root, node -> {
            if (node.isLeave()) {
                result.put(node, new Point2D(0, leavesVisited[0]));
                leavesVisited[0]++;
            } else {
                // inner node
                double y = computeYEqualLeafDepth(node, result);
                result.put(node, new Point2D(0, y)); // x gets set later
            }
        });

        // Second preOrderTraversal for x-coord calculation
        // (all edges have same length: x(w) = x(v) + 1)
        preOrderTraversal(root, node -> {
            double x = 0;
            if (!node.isRoot()) {
                x = result.get(node.parent()).getX() + 1;
            }
            Point2D oldPoint = result.get(node);
            result.put(node, new Point2D(x, oldPoint.getY()));
        });

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

    /**
     * Computes the minimum x-coordinate of the child nodes of the given node and decreases it by 1.
     * This assumes that the provided map contains the x-coordinates for all child nodes of the given node.
     *
     * @param node the node whose children's x-coordinates will be analyzed
     * @param map a mapping of nodes to their respective points containing x and y coordinates
     * @return the value of the minimum x-coordinate among child nodes minus 1
     */
    public static double computeXEqualLeafDepth(ANode node, Map<ANode, Point2D> map) {
        double min = 1;
        for (ANode child : node.children()) {
            min = Math.min(min, map.get(child).getX());
        }
        return min-1;
    }

    /**
     * Computes the average y-coordinate of all child nodes of the given node.
     * This assumes that the provided map contains the y-coordinates for all child nodes of the given node.
     *
     * @param node the node whose children's y-coordinates will be averaged
     * @param map a mapping of nodes to their respective points containing x and y coordinates
     * @return the average y-coordinate of the child nodes
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
