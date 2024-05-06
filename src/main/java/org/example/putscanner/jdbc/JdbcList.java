package org.example.putscanner.jdbc;


import org.sikuli.guide.Run;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class JdbcList {
    //properties

    //constructors

    //methods
    public void addList(String listName) {

        try {

            Connection connection = JdbcConnections.connect();
            String sql = "INSERT INTO lists (list_name)" +
                    " VALUES (?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                preparedStatement.setObject(1, listName);
                preparedStatement.executeUpdate();

            } JdbcConnections.disconnect(connection);

        } catch (SQLException e) {

            System.out.println("Error connecting to the database: " + e.getMessage());

        }

    }

    public Set<String> getAllLists() {

        Set<String> allLists = new HashSet<>();

        try {

            Connection connection = JdbcConnections.connect();
            String sql = "SELECT * FROM lists";

            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {

                    allLists.add(resultSet.getString("list_name"));

                } JdbcConnections.disconnect(connection);

            }

        } catch (SQLException e) {

            System.out.println("Error connecting to the database: " + e.getMessage());

        } return  allLists;

    }

}
