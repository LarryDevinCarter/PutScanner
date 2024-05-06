package org.example.putscanner.fxmlControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import net.sourceforge.tess4j.TesseractException;
import org.example.putscanner.jdbc.JdbcList;
import org.example.putscanner.jdbc.JdbcTicker;
import org.example.putscanner.services.ScreenRegulator;
import org.example.putscanner.services.TesseractService;
import org.sikuli.script.FindFailed;

import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import static javafx.application.Platform.exit;

public class FrontPageController implements Initializable{
    //properties
    @FXML
    public MenuButton stockListDropDownMenu;
    @FXML
    public TreeView<String> stockListTree;
    @FXML
    public TextField stockTickerTextField;
    private JdbcList jdbcList = new JdbcList();
    private JdbcTicker jdbcTicker = new JdbcTicker();

    //constructors
    public FrontPageController() {

    }

    //methods
    @FXML
    public void fileAddNewListClicked() {

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add New Stock List");
        dialog.setHeaderText("Enter list name:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(this::addToDropDownMenuAndTreeView);

    }

    @FXML
    public void fileExitClicked() {

        exit();

    }

    @FXML
    public void helpAboutClicked() {
        System.out.println("help About");
    }

    @FXML
    public void stockTickerTextFieldEnterPressed(KeyEvent key) {

        if (key.getCode() == KeyCode.ENTER && !stockTickerTextField.getText().isEmpty()) {

            addTickerToList(stockTickerTextField.getText(), stockListDropDownMenu.getText());

        }

    }

    @FXML
    public void addButtonClicked() {

        if (!stockTickerTextField.getText().isEmpty()) {

            addTickerToList(stockTickerTextField.getText(), stockListDropDownMenu.getText());

        }

    }

    @FXML
    public void scanButtonClicked() throws FindFailed {

        ScreenRegulator screenRegulator = new ScreenRegulator();
        Set<String> stocksToScan = new HashSet<>();

        for (TreeItem<String> list : stockListTree.getRoot().getChildren()) {

            if (list.getValue().equals(stockListDropDownMenu.getText())) {

                for (TreeItem<String> ticker : list.getChildren()) {

                    stocksToScan.add(ticker.getValue());

                } break;

            }

        } screenRegulator.getData(stocksToScan);

    }

    public void addToDropDownMenuAndTreeView(String text){

        MenuItem menuItem = new MenuItem(text);
        menuItem.setOnAction(e -> stockListDropDownMenu.setText(menuItem.getText()));
        System.out.println(stockListDropDownMenu);
        stockListDropDownMenu.getItems().add(menuItem);
        TreeItem<String> treeItem = new TreeItem<>(text);
        stockListTree.getRoot().getChildren().add(treeItem);
        jdbcList.addList(text);


    }

    public void addTickerToList(String text, String list) {

        jdbcTicker.addTicker(text, list);
        TreeItem<String> treeItem = new TreeItem<>(text);

        for (TreeItem<String> aList : stockListTree.getRoot().getChildren()) {

            if (aList.getValue().equals(list)) {

                aList.getChildren().add(treeItem);
                break;

            }

        } stockTickerTextField.setText("");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TreeItem<String> root = new TreeItem<>("Root");
        stockListTree.setRoot(root);
        stockListTree.setShowRoot(false);
        addStatingDataFromDatabase();

    }

    private void addStatingDataFromDatabase() {

        Set<String> allLists = jdbcList.getAllLists();

        for (String list : allLists) {

            addToDropDownMenuAndTreeView(list);
            Set<String> allTickers = jdbcTicker.getAllTickers(list);

            for (String ticker : allTickers) {

                addTickerToList(ticker, list);

            }

        }

    }

}

