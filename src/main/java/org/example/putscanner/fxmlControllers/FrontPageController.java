package org.example.putscanner.fxmlControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import net.sourceforge.tess4j.TesseractException;
import org.example.putscanner.jdbc.JdbcList;
import org.example.putscanner.jdbc.JdbcOption;
import org.example.putscanner.jdbc.JdbcTicker;
import org.example.putscanner.model.Option;
import org.example.putscanner.model.Ticker;
import org.example.putscanner.services.SikuliService;
import org.example.putscanner.services.TesseractService;
import org.sikuli.script.FindFailed;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

import static javafx.application.Platform.exit;
import static org.example.putscanner.jdbc.JdbcTicker.getTicker;

public class FrontPageController implements Initializable {
    //properties
    @FXML
    public MenuButton stockListDropDownMenu;
    @FXML
    public TreeView<String> stockListTree;
    @FXML
    public TextField stockTickerTextField;
    @FXML
    public TableView<Option> optionTable;
    @FXML
    public TableColumn<Option, String> symbol;
    @FXML
    public TableColumn<Option, BigDecimal> prRating;
    @FXML
    public TableColumn<Option, BigDecimal> profitPerWeek;
    @FXML
    public TableColumn<Option, BigDecimal> strike;
    @FXML
    public TableColumn<Option, BigDecimal> ask;
    private JdbcList jdbcList = new JdbcList();
    private JdbcTicker jdbcTicker = new JdbcTicker();
    private JdbcOption jdbcOption = new JdbcOption();
    private ObservableList<Option> data = FXCollections.observableArrayList();

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
    public void scanButtonClicked() throws FindFailed, TesseractException {

        SikuliService sikuliService = new SikuliService();
        TesseractService tesseractService = new TesseractService();
        Set<Ticker> stocksToScan = new HashSet<>();

        for (TreeItem<String> list : stockListTree.getRoot().getChildren()) {

            if (list.getValue().equals(stockListDropDownMenu.getText())) {

                for (TreeItem<String> ticker : list.getChildren()) {

                    Ticker newTicker = new Ticker(ticker.getValue());
                    stocksToScan.add(newTicker);

                } break;

            }

        } sikuliService.getImages(stocksToScan);
        jdbcOption.deleteOptions();
        data.clear();

        for (Ticker ticker : stocksToScan) {

            for (Option option : tesseractService.createStockData(getTicker(ticker.getTicker()))) {

                if (option !=  null) {

                    jdbcTicker.updateTicker(option.getTicker());
                    jdbcOption.addOption(option);
                    data.add(option);

                }

            }

        }

    }

    public void addToDropDownMenuAndTreeView(String text){

        MenuItem menuItem = new MenuItem(text);
        menuItem.setOnAction(e -> stockListDropDownMenu.setText(menuItem.getText()));
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
        symbol.setCellValueFactory(new PropertyValueFactory<Option, String>("symbol"));
        prRating.setCellValueFactory(new PropertyValueFactory<Option, BigDecimal>("prRating"));
        prRating.setSortType(TableColumn.SortType.DESCENDING);
        profitPerWeek.setCellValueFactory(new PropertyValueFactory<Option, BigDecimal>("profitPerWeek"));
        strike.setCellValueFactory(new PropertyValueFactory<Option,BigDecimal>("strike"));
        ask.setCellValueFactory(new PropertyValueFactory<Option, BigDecimal>("myAsk"));
        optionTable.setItems(data);
        addStatingDataFromDatabase(data);
        optionTable.getSortOrder().add(prRating);

    }

    private void addStatingDataFromDatabase(ObservableList<Option> data) {

        Set<String> allLists = jdbcList.getAllLists();

        for (String list : allLists) {

            addToDropDownMenuAndTreeView(list);
            Set<String> allTickers = jdbcTicker.getAllTickers(list);

            for (String ticker : allTickers) {

                addTickerToList(ticker, list);

            }

        } List<Option> allOptions = jdbcOption.getAllOptions();
        data.addAll(allOptions);

    }

}

