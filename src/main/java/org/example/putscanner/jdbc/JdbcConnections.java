package org.example.putscanner.jdbc;

import org.postgresql.*;
import java.sql.*;

public class JdbcConnections {
    public static Connection connect() throws SQLException {

        String url = "jdbc:postgresql://localhost:5432/put_scanner";
        String user = "postgres";
        String password = "djml87890921";

        try {

            Class.forName("org.postgresql.Driver");
            System.out.println("Connection opened");

        } catch (ClassNotFoundException e) {

            throw new RuntimeException("PostgreSQL JDBC Driver not found.");

        } return DriverManager.getConnection(url, user, password);

    }

    public static void disconnect(Connection connection) throws SQLException {

        if (connection != null) {

            try {

                connection.close();
                System.out.println("Connection closed.");

            } catch (SQLException e) {

                System.out.println("Error closing the connection: " + e.getMessage());
                throw e;

            }
        } else  {

            System.out.println("No connection was open, nothing to close.");

        }

    }

}
