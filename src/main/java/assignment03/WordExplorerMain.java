package assignment03;

import assignment03.model.Model;
import assignment03.model.Sorting;
import assignment03.window.WindowPresenter;
import assignment03.window.WindowView;
import javafx.application.Application;
import javafx.stage.*;

import java.util.ArrayList;
import java.util.List;

public class WordExplorerMain extends Application {

    public void start(Stage primaryStage) throws Exception {
        var view = new WindowView();
        var model = new Model();
        var presenter = new WindowPresenter(primaryStage, view.getController(), model);

        primaryStage.setTitle("Word Explorer");
        primaryStage.setScene(new javafx.scene.Scene(view.getRoot(), 800, 600));
        primaryStage.show();
    }


    public static void testSorting() {
        Integer[] t1 = {5,2,56,2,5,2,1};
        Integer[] t2 = {5,1,7,3,1,0,8};
        ArrayList<Integer> testList1 = new ArrayList<>();
        ArrayList<Integer> testList2 = new ArrayList<>();
        testList1.addAll(List.of(t1));
        testList2.addAll(List.of(t2));
        System.out.println(testList1);
        Sorting<Integer> sorter = new Sorting();
        testList1 = sorter.mergeSort(testList2);
        System.out.println(testList1);
    }
}
