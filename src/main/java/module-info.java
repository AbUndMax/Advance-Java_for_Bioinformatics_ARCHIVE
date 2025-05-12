module AdJa.git.classroom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    exports assignment02;

    opens assignment03.window to javafx.fxml;
    exports assignment03;
}