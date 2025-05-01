package assignment02;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;


public record Relation(String parentID, String parentName, String childID, String childName) {

    /**
     * reads a tab-separated file of relations and returns it as a list of Relations.
     * The expected file format:
     * first line is header. the rest is tab-separated and in four-column format: parentID parentName childID childName
     * Prints the number of relations after reading.
     *
     * @param filePath is the filepath
     * @return LinkedList<Relation>
     */
    public static LinkedList<Relation> loadFromFile(String filePath) {

        LinkedList<Relation> relations = new LinkedList<>(); //will store the relations from the file
        int counter = 0; //will count relations in the file

        //read file
        try {
            //System.err.println("attempting to read " + filePath);
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            //catch the edge case of empty file
            if (reader.readLine() == null) {
                System.err.println("Relations: loadFromFile(): File empty");
            }
            //read the file line by line
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\t");

                //append relation to relations list; catch faulty lines
                try {
                    relations.add(new Relation(fields[0].trim(), fields[1].trim(), fields[2].trim(), fields[3].trim()));
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Relations: loadFromFile(): erroneous line" + "(" + counter + "): " + Arrays.toString(fields));
                    System.exit(1);
                }
                counter++;
            }

        } catch (FileNotFoundException e) {
            System.err.println("Relations: loadFromFile(): File not found");
        }
        catch (IOException e) {
            System.err.println("Something happened");
        }
        System.out.println("Relations: " + counter);
        return relations;
    }

}
