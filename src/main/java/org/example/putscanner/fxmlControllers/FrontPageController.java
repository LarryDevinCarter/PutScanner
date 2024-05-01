package org.example.putscanner.fxmlControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class FrontPageController implements Initializable{

    @FXML
    public MenuButton stockListDropDownMenu;
    @FXML
    public TreeView<String> stockListTree;
    @FXML
    public TextField stockTickerTextField;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TreeItem<String> root = new TreeItem<>("Root");
        stockListTree.setRoot(root);
        stockListTree.setShowRoot(false);

    }
}