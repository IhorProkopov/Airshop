package com.epam.prokopov.shop.repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Execute query, which contains in {@link Transaction}.
 */
public class TransactionHelper {

    private final DataSource dataSource;


    public TransactionHelper(DataSource dataSource) {
        this.dataSource = dataSource;

    }

    public <T> T execute(Transaction transaction) throws DAOException {
        T result = null;

        Connection connection = null;

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);

            result = transaction.execute(connection);

            connection.commit();

            connection.close();
        } catch (SQLException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                throw new DAOException(e1.getMessage());
            }
            throw new DAOException(e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

}