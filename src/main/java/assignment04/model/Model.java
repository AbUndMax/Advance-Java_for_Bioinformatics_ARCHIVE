package assignment04.model;

import java.io.IOException;

/**
 * Loads the Tree from the files and stores the root.
 */
public class Model {
    private final ANode tree; // ALL OPERATIONS are applied to this tree instance!

    public Model() throws IOException {
        tree = TreeLoader.load("src/main/resources/asgmt02_res/partof_parts_list_e.txt",
                               "src/main/resources/asgmt02_res/partof_element_parts.txt",
                               "src/main/resources/asgmt02_res/partof_inclusion_relation_list.txt");
    }

    public ANode getTree() {
        return tree;
    }
}