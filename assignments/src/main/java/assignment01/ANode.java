package assignment01;

import java.util.HashMap;
import java.util.LinkedList;

/*
Author: Luis Reimer, Niklas Gerbes
 */
public record ANode(String ID, String name, LinkedList<ANode> children) {

    // Function that creates a Tree structure based on the Relation List
    public static ANode createTree(LinkedList<Relation> relations) {
        HashMap<String, ANode> idToNode = new HashMap<>();

        for (Relation relation : relations) {
            ANode parentNode = idToNode.getOrDefault(relation.parentID(), new ANode(relation.parentID(), relation.parentName(), new LinkedList<>()));
            ANode childNode = idToNode.getOrDefault(relation.childID(), new ANode(relation.childID(), relation.childName(), new LinkedList<>()));
            parentNode.children.add(childNode);
            idToNode.putIfAbsent(relation.parentID(), parentNode);
            idToNode.putIfAbsent(relation.childID(), childNode);
        }

        System.out.println("Tree: " + idToNode.size());

        return idToNode.get("FMA20394");
    }

    /*
    Helper function of higher order to traverse the tree and generate path strings
     */
    public static void traversePaths(ANode node, String path, java.util.function.Consumer<String> action) {
        if (node.children.isEmpty()) {
            action.accept(path + node.name());
            return;
        }

        for (ANode child : node.children) {
            traversePaths(child, path + node.name() + " -> ", action);
        }
    }

    /*
    Helper function to see whether any word from a list is contained in a string
     */
    public static boolean containsAny(String string, String[] words) {
        for (String word : words) {
            if (string.contains(word)) {
                return true;
            }
        }
        return false;
    }

    /*
    prints all paths of the tree liek this:
        human body -> alimentary system -> mouth -> tongue
        human body -> alimentary system -> mouth -> soft palate -> right levator veli palatini
        ...
     */
    public static void printAllPaths(ANode tree) {
        traversePaths(tree, "", System.out::println);
    }

    /*
    prints those paths of the tree that contain a specific word, similar formatted like @printAllPaths
     */
    public static void printPathContaining(ANode node, String[] words) {
        traversePaths(node, "", fullPath -> {
            if (containsAny(fullPath, words)) {
                System.out.println(fullPath);
            }
        });
    }
}