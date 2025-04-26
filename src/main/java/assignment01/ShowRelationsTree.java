package assignment01;

import ArgsParser.ArgsParser;
import ArgsParser.ParameterTypes.PthParameter;
import ArgsParser.ParameterTypes.StrArrParameter;

import java.nio.file.Paths;
import java.util.LinkedList;

/*
Author: Niklas Gerbes
 */
public class ShowRelationsTree {
    public static void main(String[] args) {

        // using ArgsParser (https://github.com/AbUndMax/Java_ArgsParser, Author: Niklas Gerbes) for CLI argument parsing!
        ArgsParser parser = new ArgsParser();
        // Argument to set a read in path to "partof_inclusion_relation_list.txt". First value is a default.
        PthParameter fileToLoadFrom = parser.addParameter(
                new PthParameter(Paths.get("../AdJa_assignemnts/Assignment01/partof_inclusion_relation_list.txt"),
                        "filePath", "fp", "Path to the 'part_of_inclusion_relation' file", true));

        // Argument for a list of words to filter the output
        StrArrParameter filterWords = parser.addParameter(
                new StrArrParameter("filter", "f", "Any number of space seperated words that should be used to " +
                        "filter the output paths", false));

        // parse the arguments
        parser.parse(args);

        // load "partof_inclusion_relation_list.txt" file
        LinkedList<Relation> loadedRelations = Relation.loadFromFile(fileToLoadFrom.getArgument().toFile());

        // create Tree
        ANode tree = ANode.createTree(loadedRelations);

        // custom printouts
        if (filterWords.isProvided()) {
            ANode.printPathContaining(tree, filterWords.getArgument());
        } else {
            ANode.printAllPaths(tree);
        }
    }
}
