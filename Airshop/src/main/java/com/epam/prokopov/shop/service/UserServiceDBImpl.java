package com.epam.prokopov.shop.service;


import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.model.UserLoginInfo;
import com.epam.prokopov.shop.repository.DAOException;
import com.epam.prokopov.shop.repository.Transaction;
import com.epam.prokopov.shop.repository.TransactionHelper;
import com.epam.prokopov.shop.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class UserServiceDBImpl implements UserService {

    private UserRepository userRepository;
    private TransactionHelper transactionHelper;

    public UserServiceDBImpl(DataSource dataSource, UserRepository userRepository) {
        this.transactionHelper = new TransactionHelper(dataSource);
        this.userRepository = userRepository;
    }

    @Override
    public void addNewUser(final User user) throws DAOException {
        transactionHelper.execute(new Transaction() {
            @Override
            public <T> T execute(Connection connection) throws SQLException {
                userRepository.addNewUser(connection, user);
                return null;
            }
        });
    }

    @Override
    public boolean exist(final String email) throws DAOException {
        return transactionHelper.execute(new Transaction() {
            @Override
            public Boolean execute(Connection connection) throws SQLException {
                return userRepository.exist(connection, email);
            }
        });
    }

    @Override
    public void removeUser(final String email) throws DAOException {
        transactionHelper.execute(new Transaction() {
            @Override
            public <T> T execute(Connection connection) throws SQLException {
                userRepository.removeUser(connection, email);
                return null;
            }
        });
    }

    @Override
    public void editUser(final User user, final UserLoginInfo userLoginInfo) throws DAOException {
        transactionHelper.execute(new Transaction() {
            @Override
            public <T> T execute(Connection connection) throws SQLException {
                userRepository.editUser(connection, user, userLoginInfo);
                return null;
            }
        });
    }

    @Override
    public User getUser(final String email) throws DAOException {
        return transactionHelper.execute(new Transaction() {
            @Override
            public User execute(Connection connection) throws SQLException {
                return userRepository.getUser(connection, email);
            }
        });
    }

    @Override
    public boolean checkPassword(final String email, final String password) throws DAOException {
        return transactionHelper.execute(new Transaction() {
            @Override
            public Boolean execute(Connection connection) throws SQLException {
                User u = userRepository.getUser(connection, email);
                if (u != null) {
                    if (u.getPass().equals(password)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
