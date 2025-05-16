package assignment04.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a node in a hierarchical tree structure, such as a phylogenetic or organizational tree.
 * Each node can have a unique concept ID and representation ID, a name, a parent node, and a collection
 * of child nodes and associated file identifiers.
 */
public record ANode(String conceptId, String representationId, String name, ANode parent, LinkedList<ANode> children, Collection<String> fileIds) {

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

    protected boolean hasChildren() {
        return !children.isEmpty();
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

    /**
     * Converts the tree structure rooted at the current node into Newick format.
     *
     * @return a String representing the tree structure in Newick format, ending with a semicolon
     */
    public String toNewick() {
        return buildNewick(this) + ";";
    }

    /**
     * Recursively builds a Newick representation of the tree structure starting from the given node.
     * The Newick format represents tree structures using nested, parenthetical notation.
     *
     * @param node the root node for which the Newick representation is to be built
     * @return a String representing the subtree rooted at the given node in Newick format
     */
    private String buildNewick(ANode node) {
        if (node.isLeave()) {
            return node.name;
        } else {
            String childrenNewick = node.children().stream()
                    .map(this::buildNewick)
                    .collect(Collectors.joining(","));
            return "(" + childrenNewick + ")" + node.name();
        }
    }

    /**
     * Creates a deep copy of this node and its entire subtree.
     *
     * @return a copy of this node and its descendants as a new tree.
     */
    public ANode copyTree() {
        return copyTreeInternal(null);
    }

    /**
     * Internal helper for recursive deep copy, linking each new node to its parent.
     *
     * @param parent the parent for the newly copied node
     * @return the copied node with its subtree
     */
    private ANode copyTreeInternal(ANode parent) {
        LinkedList<ANode> newChildren = new LinkedList<>();
        Collection<String> newFileIds = fileIds() != null
                ? new LinkedList<>(fileIds())
                : new LinkedList<>();
        ANode copy = new ANode(
                conceptId(),
                representationId(),
                name(),
                parent,
                newChildren,
                newFileIds
        );
        for (ANode child : children()) {
            ANode childCopy = child.copyTreeInternal(copy);
            newChildren.add(childCopy);
        }
        return copy;
    }

    /**
     * Filters the tree structure by removing nodes and their subtrees that do not satisfy the given filter condition.
     * The filter is applied recursively, and nodes are retained if their name contains the specified filter text
     * or if any of their descendants match the criteria.
     *
     * NOTE: the original tree does not exist any more! NON MATCHING PATHS GET REMOVED
     *
     * @param filter the string to filter nodes by; nodes whose name contains this string or have matching descendants
     *               are retained.
     * @return the root node (this node) of the potentially modified tree structure after filtering.
     */
    public ANode filterTree(String filter) {
        filterTree(this, filter);
        return this;
    }

    /**
     * Recursively filters the tree, starting from the given node, based on the provided filter string.
     * Nodes whose names contain the filter string or have descendants that match the filter are retained,
     * while others are removed from the tree. The operation modifies the tree in place.
     *
     * @param node the current node being processed during the recursive filtering
     * @param filter the string to filter nodes by; nodes whose names contain this string or have matching descendants
     *               are retained
     * @return true if the current node or any of its descendants matches the filter; false otherwise
     */
    private static boolean filterTree(ANode node, String filter) {
        boolean anyHitInChildren = false;
        Iterator<ANode> iterator = node.children().iterator();
        while (iterator.hasNext()) {
            ANode child = iterator.next();
            if (!filterTree(child, filter)) {
                iterator.remove();

            } else {
                anyHitInChildren = true;
            }
        }

        return anyHitInChildren || node.name().contains(filter);
    }

}