package org.example.putscanner.jdbc;

import org.example.putscanner.model.Option;
import org.example.putscanner.model.Ticker;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcOption {
    //methods
    public void addOption(Option option) {

        try {

            Connection connection = JdbcConnections.connect();
            String sql = "INSERT INTO options (ticker, expiration, strike, bid, ask, last)" +
                    " VALUES (?,?,?,?,?,?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setObject(1, option.getTicker().getTicker());
                preparedStatement.setObject(2, option.getExpiration());
                preparedStatement.setObject(3, option.getStrike());
                preparedStatement.setObject(4, option.getBid());
                preparedStatement.setObject(5, option.getAsk());
                preparedStatement.setObject(6, option.getLast());
                preparedStatement.executeUpdate();

            } JdbcConnections.disconnect(connection);

        } catch (SQLException e) {

            System.out.println("Error connecting to the database: " + e.getMessage());

        }

    }

    public void deleteOptions() {

        try {

            Connection connection = JdbcConnections.connect();
            String sql = "DELETE FROM options;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.executeUpdate();

            } JdbcConnections.disconnect(connection);

        } catch (SQLException e) {

            System.out.println("Error connecting to the database: " + e.getMessage());

        }

    }

    public List<Option> getAllOptions() {

        List<Option> allOptions = new ArrayList<>();

        try {

            Connection connection = JdbcConnections.connect();
            String sql = "SELECT * FROM options";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    Option option = new Option(resultSet.getString("ticker"), resultSet.getDate("expiration"), new BigDecimal(resultSet.getString("strike").replaceAll("[,$]", "")), new BigDecimal(resultSet.getString("bid").replaceAll("[,$]", "")), new BigDecimal(resultSet.getString("ask").replaceAll("[,$]", "")), new BigDecimal(resultSet.getString("last").replaceAll("[,$]", "")));
                    allOptions.add(option);

                } JdbcConnections.disconnect(connection);

            }

        } catch (SQLException e) {

            System.out.println("Error connecting to the database: " + e.getMessage());

        } return  allOptions;

    }

}
