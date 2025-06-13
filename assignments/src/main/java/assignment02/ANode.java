package assignment02;

import java.util.Collection;

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
public record ANode(String conceptId, String representationId, String name, Collection<ANode> children, Collection<String> fileIds) {

    public String toString() {
        return name + " (" + conceptId + ")";
    }
    protected boolean addChild(ANode child) {
        return children.add(child);
    }
    protected boolean removeChild(ANode child) {
        return children.remove(child);
    }

    protected boolean addFileID(String fileID) {
        return fileIds.add(fileID);
    }

    protected boolean removeFileID(String fileID) {
        return fileIds.remove(fileID);
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
