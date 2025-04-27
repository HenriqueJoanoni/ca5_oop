package org.oop.ca5_oop.DAO;

import org.oop.ca5_oop.Exception.DaoException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDao {

    /**
     * Start connection.
     *
     * @return the connection
     * @throws DaoException the dao exception
     */
    public Connection startConnection() throws DaoException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/firestrings_oop";
        String username = "root";
        String password = "12345";
        Connection conn = null;

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Failed to find driver class " + e.getMessage());
            System.exit(1);
        } catch (SQLException e) {
            System.out.println("Connection failed " + e.getMessage());
            System.exit(2);
        }
        return conn;
    }

    /**
     * Stop connection.
     *
     * @param conn the connection object
     * @throws DaoException the dao exception
     */
    public void stopConnection(Connection conn) throws DaoException {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException e) {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }
}
