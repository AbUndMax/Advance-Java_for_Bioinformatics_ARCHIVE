package assignment02;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ANode root = TreeLoader.load(
                "src/main/resources/asgmt02_res/partof_element_parts.txt",
                "src/main/resources/asgmt02_res/partof_parts_list_e.txt",
                "src/main/resources/asgmt02_res/partof_inclusion_relation_list.txt");
        String[] filters = new String[] {"hand", "bone"};
        ANode.printTree(root, filters);
        System.out.println(root.toString());

    }

}
