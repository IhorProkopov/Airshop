package com.epam.prokopov.shop.service;


import com.epam.prokopov.shop.model.UserLoginInfo;
import com.epam.prokopov.shop.repository.DAOException;
import com.epam.prokopov.shop.repository.Transaction;
import com.epam.prokopov.shop.repository.TransactionHelper;
import com.epam.prokopov.shop.repository.UserLoginInfoRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class UserLoginInfoServiceDBImpl implements UserLoginInfoService {

    private UserLoginInfoRepository userLoginInfoRepository;
    private TransactionHelper transactionHelper;

    public UserLoginInfoServiceDBImpl(DataSource dataSource, UserLoginInfoRepository userLoginInfoRepository) {
        this.transactionHelper = new TransactionHelper(dataSource);
        this.userLoginInfoRepository = userLoginInfoRepository;
    }

    @Override
    public UserLoginInfo getUserLoginInfo(final String email) throws DAOException {
        return transactionHelper.execute(new Transaction() {
            @Override
            public UserLoginInfo execute(Connection connection) throws SQLException {
                return userLoginInfoRepository.getUserLoginInfo(connection, email);
            }
        });
    }

    @Override
    public void setUserLoginInfo(final UserLoginInfo userLoginInfo) throws DAOException {
        transactionHelper.execute(new Transaction() {
            @Override
            public <T> T execute(Connection connection) throws SQLException {
                userLoginInfoRepository.setUserLoginInfo(connection, userLoginInfo);
                return null;
            }
        });
    }
}
