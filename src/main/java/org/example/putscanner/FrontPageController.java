package org.example.putscanner;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FrontPageController {

    @FXML
    public void fileAddNewListClicked() {
        System.out.println("file Add New List...");;
    }

    public void fileExitClicked() {
        System.out.println("file Exit");
    }

    public void helpAboutClicked() {
        System.out.println("help About");
    }
}