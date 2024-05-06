package org.example.putscanner.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class JdbcTicker {
    //methods
    public void addTicker(String tickerSymbol, String listName) {

        try {

            Connection connection = JdbcConnections.connect();
            String tickersSql = "INSERT INTO tickers (ticker)" +
                    " VALUES (?)";
            String listsTickersSql = "INSERT INTO lists_tickers (list_name, ticker)" +
                    " VALUES (?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(tickersSql)) {

                preparedStatement.setObject(1, tickerSymbol.toUpperCase());
                preparedStatement.executeUpdate();

            } try (PreparedStatement preparedStatement = connection.prepareStatement(listsTickersSql)) {

                preparedStatement.setObject(1, listName);
                preparedStatement.setObject(2, tickerSymbol.toUpperCase());
                preparedStatement.executeUpdate();

            } JdbcConnections.disconnect(connection);

        } catch (SQLException e) {

            System.out.println("Error connecting to the database: " + e.getMessage());

        }

    }

    public Set<String> getAllTickers(String list) {

        Set<String> allTickers = new HashSet<>();

        try {

            Connection connection = JdbcConnections.connect();
            String sql = "SELECT * FROM lists_tickers WHERE list_name = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setObject(1, list);
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    allTickers.add(resultSet.getString("ticker"));

                } JdbcConnections.disconnect(connection);

            }

        } catch (SQLException e) {

            System.out.println("Error connecting to the database: " + e.getMessage());

        } return  allTickers;

    }

}
