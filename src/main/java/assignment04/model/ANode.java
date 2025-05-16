package assignment04.model;

import java.util.Collection;
import java.util.Objects;

/**
 * New implementation of ANode! (DIFFERENT TO ANode FROM ASSIGNMENT01!)
 * @param conceptId
 * @param representationId
 * @param name
 * @param children
 * @param fileIds
 *
 * @author Luis Reimer, Niklas Gerbes
 */
public record ANode(String conceptId, String representationId, String name, ANode parent, Collection<ANode> children, Collection<String> fileIds) {

    public String toString() {
        return name + " (" + conceptId + ")";
    }

    protected boolean addChild(ANode child) {
        return children.add(child);
    }

    protected boolean isLeave() {
        return children.isEmpty();
    }

    protected boolean isRoot() {
        return parent == null;
    }

    /**
     * Computes and returns the number of leaf nodes in a tree starting from the current node.
     * A leaf node is defined as a node without any children.
     *
     * @return the total number of leaf nodes in the subtree rooted at the current node
     */
    public int getNumberOfLeaves() {
        int[] numberOfLeaves = {0};
        Cladogram.postOrderTraversal(this, node -> {
            if (node.isLeave()) numberOfLeaves[0]++;
        });
        return numberOfLeaves[0];
    }

    /**
     * Calculates the maximum horizontal depth of the tree starting from the current node.
     * The horizontal depth is determined by the maximum number of internal nodes along a path
     * from the root to a leaf in the tree. An internal node is considered part of the depth only
     * if it has at least one child.
     *
     * @return the maximum horizontal depth of the tree as an integer
     */
    public int horizontalTreeDepth() {
        if (children == null || children.isEmpty()) {
            return 1;
        }
        return 1 + children.stream()
                .mapToInt(ANode::horizontalTreeDepth)
                .max()
                .orElse(0);
    }

    /**
     * Override of hashCode to prevent recursive calls.
     * Fields conceptId, representationId, and name are used in the hash code calculation.
     */
    @Override
    public int hashCode() {
        return Objects.hash(conceptId, representationId, name);
    }

    /**
     * Prints all paths in the subtree below a node (only paths that end in a leaf, not internal node).
     * Keywords may be added to only print those paths which include all the keywords.
     * Works as a wrapper for the private recursive collectPrint() function
     * @param root as the node whose subtree is to be printed
     * @param filters as a String Array of keywords.
     */
    public static void printTree(ANode root, String[] filters) {
        System.out.println(collectPrint(root, "", filters));
    }

    /**
     * Does the computation required by printTree(). Hands back the complete String, ready to be printed to stdout.
     * @param root
     * @param p
     * @param filters
     * @return
     */
    private static String collectPrint(ANode root, String p, String[] filters) {
        p += root.name;
        StringBuilder pBuilder = new StringBuilder();

        for (ANode child : root.children) {
            pBuilder.append(collectPrint(child, p+ "->", filters));
        }
        if (root.children.isEmpty()) { //avoid printing paths that end in internal nodes
            pBuilder.append(p).append("\n");
        }
        String res = pBuilder.toString();
        for (String filter : filters) { //avoid printing paths that dont contain the keywords
            if (!res.contains(filter)) {
                return "";
            }
        }
        return res;
    }

}