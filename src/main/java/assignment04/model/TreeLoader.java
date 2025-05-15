package assignment04.model;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

/**
 * Utility class responsible for loading a tree structure from tab-separated data files.
 * <p>
 * The class builds a tree of {@link ANode} objects using concept and relation data
 * provided in the input files. It expects:
 * <ul>
 *   <li>A parts file mapping concept IDs to representation IDs</li>
 *   <li>An elements file mapping concept IDs to associated filenames</li>
 *   <li>A relations file describing parent-child relationships between concepts</li>
 * </ul>
 *
 * This class is intended to be used for constructing a single-rooted tree structure
 * which represents hierarchical relations between concepts.
 *
 * All files must contain a header line and be tab-separated.
 *
 * @author Luis Reimer, Niklas Gerbes
 */
public class TreeLoader {

    /**
     *
     * All files must be tab separated and are assumed to have one header line.
     * @param partsFile of format: concept id | representation id | name
     * @param elementsFile of format: concept id | name | filename
     * @param relationsFile of format: concept id (parent) | name (parent) | concept id (child) | name (child)
     * @return the root of the tree
     */
    public static ANode load(String partsFile, String elementsFile, String relationsFile) {

        HashMap<String, String> conceptIDtoRepID = mapConceptIDRepID(partsFile);
        HashMap<String, Collection<String>> fileList  = loadFileList (elementsFile);
        LinkedList<Relation> relations = Relation.loadFromFile(new File(relationsFile));

        if (conceptIDtoRepID == null || fileList == null || relations.isEmpty()) {
            return null;
        }

        return createTree(relations, conceptIDtoRepID, fileList);
    }


    /**
     * From a List of Relations, generates ANode objects with the appropriate child-relations.
     * Hands back the master root.
     * Assumes that the list of Relations represents one tree. otherwise there may be bugs (?)
     */
    private static ANode createTree(LinkedList<Relation> relations, HashMap<String, String> conceptIDtoRepID, HashMap<String, Collection<String>> fileList) {

        HashMap<String, ANode> nodes = new HashMap<>(); //all nodes
        HashSet<String> notRootID = new HashSet<>(); //all nodes that are children
        //the set and map will be compared to find the node that is the root

        for (Relation relation : relations) {
            String parentID = relation.parentID();
            String parentName = relation.parentName();
            String childID = relation.childID();
            String childName = relation.childName();

            //public record ANode(String conceptId, String representationId, String name, Collection<ANode> children, Collection<String> fileIds)
            //get child/parent
            ANode child = nodes.containsKey(childID) ? nodes.get(childID) : new ANode(childID, conceptIDtoRepID.get(childID), childName, new LinkedList<ANode>(), fileList.get(childID));
            ANode parent = nodes.containsKey(parentID) ? nodes.get(parentID) : new ANode(parentID, conceptIDtoRepID.get(parentID), parentName, new LinkedList<ANode>(), fileList.get(parentID));
            //add child to parents children
            parent.addChild(child);

            nodes.put(childID, child);
            nodes.put(parentID, parent);

            notRootID.add(child.conceptId());

        }

        //find the root
        ANode root = null;
        for (String ID : nodes.keySet()) {
            if (!notRootID.contains(ID)) {
                root = nodes.get(ID);
            }
        }

        return root;
    }

    /**
     * Generates a mapping of conceptId to representationId.
     * @param file as path
     * @return the mapping
     */
    private static HashMap<String, String> mapConceptIDRepID(String file) {

        HashMap<String, String> conceptToRepresentationMap = new HashMap<>();

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            if ((fileReader.readLine())==null) {
                System.err.println("TreeLoader: load(): partsFile empty.");
                return null;
            }
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] lineArr = line.split("\t");
                conceptToRepresentationMap.put(lineArr[0].trim(), lineArr[1].trim());
            }
        } catch (FileNotFoundException e) {
            System.err.println("TreeLoader: load(): partsFile not found.");
            return null;
        } catch (IOException e) {
            System.err.println("TreeLoader: load(): Something happened with partsFile");
            return null;
        }

        return conceptToRepresentationMap;
    }

    private static HashMap<String, Collection<String>> loadFileList(String file) {
        HashMap<String, Collection<String>> IDtoFilelist = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            if ((reader.readLine()==null)) {
                System.err.println("TreeLoader: load(): elementsFile empty.");
                return null;
            }
            String line;
            while ((line=reader.readLine()) != null) {
                String[] lineArr = line.split("\t");
                String conceptID = lineArr[0].trim();
                Collection<String> fileList = IDtoFilelist.containsKey(conceptID) ? IDtoFilelist.get(conceptID) : new LinkedList<String>();
                fileList.add(lineArr[2].trim());
                IDtoFilelist.put(conceptID, fileList);
            }
        } catch (FileNotFoundException e) {
            System.err.println("TreeLoader: load(): elementsFile not found.");
            return null;
        } catch (IOException e) {
            System.err.println("TreeLoader: load(): Something happened with elementsFile");
            return null;
        }
        return IDtoFilelist;
    }

}
