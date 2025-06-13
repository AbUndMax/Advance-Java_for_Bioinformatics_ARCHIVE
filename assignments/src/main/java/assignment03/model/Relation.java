package assignment03.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

/*
Author: Luis Reimer, Niklas Gerbes
 */
public record Relation(String parentID, String parentName, String childID, String childName) {

    // Function to load a tab seperated values file of format:
    // parentID \t parentName \t childID \t childName
    // and returns a list of instantiated Relation objects
    public static LinkedList<Relation> loadFromFile(File file) {
        LinkedList<Relation> relations = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                String parentID = parts[0].trim();
                String parentName = parts[1].trim();
                String childID = parts[2].trim();
                String childName = parts[3].trim();
                relations.add(new Relation(parentID, parentName, childID, childName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return relations;
    }
}
