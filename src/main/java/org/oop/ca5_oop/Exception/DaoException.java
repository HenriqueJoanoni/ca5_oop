package org.oop.ca5_oop.Exception;

import java.sql.SQLException;

public class DaoException extends SQLException {

    public DaoException(String message) {
        super(message);
    }
}
