package org.example.putscanner.fxmlControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.example.putscanner.services.ScreenRegulator;
import org.sikuli.script.FindFailed;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

public class FrontPageController implements Initializable{
    //properties
    @FXML
    public MenuButton stockListDropDownMenu;
    @FXML
    public TreeView<String> stockListTree;
    @FXML
    public TextField stockTickerTextField;

    //methods
    @FXML
    public void fileAddNewListClicked() throws IOException {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Stock List");
        dialog.setHeaderText("Enter list name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::addDropDownMenuAndTreeView);

    }

    @FXML
    public void fileExitClicked() {
        System.out.println("file Exit");
    }

    @FXML
    public void helpAboutClicked() {
        System.out.println("help About");
    }

    @FXML
    public  void addDropDownMenuAndTreeView(String text){

        MenuItem menuItem = new MenuItem(text);
        menuItem.setOnAction(e -> stockListDropDownMenu.setText(menuItem.getText()));
        stockListDropDownMenu.getItems().add(menuItem);
        TreeItem<String> treeItem = new TreeItem<>(text);
        stockListTree.getRoot().getChildren().add(treeItem);

    }

    @FXML
    public void addButtonClicked() {

        TreeItem<String> treeItem = new TreeItem<>(stockTickerTextField.getText());

        for (TreeItem<String> list : stockListTree.getRoot().getChildren()) {

            if (list.getValue().equals(stockListDropDownMenu.getText())) {

                list.getChildren().add(treeItem);
                break;

            }

        }

    }

    @FXML
    public void scanButtonClicked() throws FindFailed, InterruptedException {

        ScreenRegulator screenRegulator = new ScreenRegulator();
        Set<String> stocksToScan = new HashSet<>();

        for (TreeItem<String> list : stockListTree.getRoot().getChildren()) {

            if (list.getValue().equals(stockListDropDownMenu.getText())) {

                for (TreeItem<String> ticker : list.getChildren()) {

                    stocksToScan.add(ticker.getValue());

                }

            } break;

        } screenRegulator.getData(stocksToScan);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TreeItem<String> root = new TreeItem<>("Root");
        stockListTree.setRoot(root);
        stockListTree.setShowRoot(false);

    }
}