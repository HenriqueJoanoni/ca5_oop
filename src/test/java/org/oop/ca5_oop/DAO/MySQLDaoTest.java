package org.oop.ca5_oop.DAO;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class MySQLDaoTest {

    @Test
    void startConnectionTest() {
        MySQLDao dao = new MySQLDao();
        Connection conn = null;
        try {
            conn = dao.startConnection();
            assertNotNull(conn, "Connection should not be null");

            assertTrue(conn.isValid(2), "Connection should be valid");

            assertFalse(conn.isClosed(), "Connection should be open");
        } catch (SQLException e) {
            fail("SQLException occurred while checking connection", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
}