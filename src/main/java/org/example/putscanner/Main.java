package org.example.putscanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.putscanner.fxmlControllers.FrontPageController;
import org.example.putscanner.jdbc.JdbcList;
import org.example.putscanner.jdbc.JdbcTicker;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("front-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Put Scanner");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch();

    }

}