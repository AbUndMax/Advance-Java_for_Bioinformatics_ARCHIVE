package assignment04.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a node in a hierarchical tree structure, such as a phylogenetic or organizational tree.
 * Each node can have a unique concept ID and representation ID, a name, a parent node, and a collection
 * of child nodes and associated file identifiers.
 */
public class ANode {
    private final String conceptId;
    private final String representationId;
    private final String name;
    private ANode parent;
    private final LinkedList<ANode> children = new LinkedList<>();
    private final Collection<String> fileIds;
    private boolean filteredOut = false; // this variable specifies if this nodes gets drawn or not

    public ANode(String conceptId, String representationId, String name, Collection<String> fileIds) {
        this.conceptId = conceptId;
        this.representationId = representationId;
        this.name = name;
        this.fileIds = fileIds;
    }

    public String getConceptId() {
        return conceptId;
    }

    public String getName() {
        return name;
    }

    public Collection<String> getFileIds() {
        return fileIds;
    }

    public ANode getParent() {
        return parent;
    }

    public void setParent(ANode parent) {
        this.parent = parent;
    }

    /**
     * Retrieves a filtered list of this node's children.
     * Only includes children that have not been marked as filtered out.
     *
     * @return a LinkedList containing the non-filtered child nodes of this node
     */
    public LinkedList<ANode> getChildren() {
        return children.stream().filter(child -> !child.isFilteredOut())
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Retrieves the list of child nodes associated with this node.
     *
     * @return a LinkedList containing the child nodes of this node
     */
    public LinkedList<ANode> getAllChildren() {
        return children;
    }

    protected boolean isLeave() {
        return this.getChildren().isEmpty();
    }

    protected boolean isRoot() {
        return parent == null;
    }

    public String toString() {
        return name + " (" + conceptId + ")";
    }

    protected void addChild(ANode child) {
        children.add(child);
    }

    /**
     * Checks whether this node has been marked as filtered out.
     * A node is considered filtered out if it has been explicitly marked
     * so, typically indicating exclusion from further processing or evaluation.
     *
     * @return true if the node is marked as filtered out, false otherwise
     */
    public boolean isFilteredOut() {
        return filteredOut;
    }

    /**
     * Marks this node as filtered out or not filtered out based on the provided value.
     * A filtered out node is excluded from further processing or evaluation.
     *
     * @param filteredOut a boolean value indicating whether the node should be marked as filtered out (true) or not (false)
     */
    public void filteredOut(boolean filteredOut) {
        this.filteredOut = filteredOut;
    }

    protected boolean hasChildren() {
        return !children.isEmpty();
    }

    /**
     * Computes and returns various metrics about the tree structure starting from the current node.
     * The method performs a post-order traversal of the tree and calculates:
     * - The total number of nodes in the tree.
     * - The total number of leaf nodes (nodes without children).
     * - The total number of edges in the tree.
     *
     * @return an integer array containing three elements:
     *         - [0] The total number of nodes.
     *         - [1] The total number of leaf nodes.
     *         - [2] The total number of edges.
     */
    public int[] nodeMetrics() {
        int[] nodes = {0};
        int[] leaves = {0};

        Cladogram.postOrderTraversal(this, node -> {
            nodes[0]++;
            if (node.isLeave()) {
                leaves[0]++;
            }
        });

        int edges = nodes[0] - 1;

        return new int[]{nodes[0], leaves[0], edges};
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
     * Only non-filtered nodes are saved!
     *
     * @param node the root node for which the Newick representation is to be built
     * @return a String representing the subtree rooted at the given node in Newick format
     */
    private String buildNewick(ANode node) {
        if (node.isLeave()) {
            return node.name;
        } else {
            String childrenNewick = node.getChildren().stream()
                    .map(this::buildNewick)
                    .collect(Collectors.joining(","));
            return "(" + childrenNewick + ")" + node.getName();
        }
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
    public ANode applyFilter(String filter) {
        filterNode(this, filter);
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
    private static boolean filterNode(ANode node, String filter) {
        boolean anyHitInChildren = false;
        for (ANode child : node.getAllChildren()) {
            if (!filterNode(child, filter)) {
                child.filteredOut(true);

            } else {
                child.filteredOut(false);
                anyHitInChildren = true;
            }
        }

        return anyHitInChildren || node.getName().contains(filter);
    }

}