module AdJa.git.classroom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    exports assignment02;

    opens assignment03.window to javafx.fxml;
    exports assignment03;

    opens assignment04.window to javafx.fxml;
    exports assignment04;

    opens assignment05.window to javafx.fxml;
    exports assignment05;
}