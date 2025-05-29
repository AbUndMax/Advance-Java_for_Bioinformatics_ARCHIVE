package assignment06.window;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;

public class WindowController {

    @FXML
    private MenuItem clearMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private Button closeButton;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem openMenuItem;

    @FXML
    private Button resetButton;

    @FXML
    private MenuItem resetMenuItem;

    @FXML
    private Button rotateDownButton;

    @FXML
    private MenuItem rotateDownMenuItem;

    @FXML
    private Button rotateLeftButton;

    @FXML
    private MenuItem rotateLeftMenuItem;

    @FXML
    private Button rotateRightButton;

    @FXML
    private MenuItem rotateRightMenuItem;

    @FXML
    private Button rotateUpButton;

    @FXML
    private MenuItem rotateUpMenuItem;

    @FXML
    private Pane threeDpane;

    @FXML
    private Button zoomInButton;

    @FXML
    private MenuItem zoomInMenuItem;

    @FXML
    private Button zoomOutButton;

    @FXML
    private MenuItem zoomOutMenuItem;

    public MenuItem getClearMenuItem() {return clearMenuItem;}

    public MenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public Button getCloseButton() {
        return closeButton;
    }

    public MenuItem getCloseMenuItem() {
        return closeMenuItem;
    }

    public MenuItem getOpenMenuItem() {
        return openMenuItem;
    }

    public Button getResetButton() {
        return resetButton;
    }

    public MenuItem getResetMenuItem() {
        return resetMenuItem;
    }

    public Button getRotateDownButton() {
        return rotateDownButton;
    }

    public MenuItem getRotateDownMenuItem() {
        return rotateDownMenuItem;
    }

    public Button getRotateLeftButton() {
        return rotateLeftButton;
    }

    public MenuItem getRotateLeftMenuItem() {
        return rotateLeftMenuItem;
    }

    public Button getRotateRightButton() {
        return rotateRightButton;
    }

    public MenuItem getRotateRightMenuItem() {
        return rotateRightMenuItem;
    }

    public Button getRotateUpButton() {
        return rotateUpButton;
    }

    public MenuItem getRotateUpMenuItem() {
        return rotateUpMenuItem;
    }

    public Pane getThreeDpane() {
        return threeDpane;
    }

    public Button getZoomInButton() {
        return zoomInButton;
    }

    public MenuItem getZoomInMenuItem() {
        return zoomInMenuItem;
    }

    public Button getZoomOutButton() {
        return zoomOutButton;
    }

    public MenuItem getZoomOutMenuItem() {
        return zoomOutMenuItem;
    }
}
