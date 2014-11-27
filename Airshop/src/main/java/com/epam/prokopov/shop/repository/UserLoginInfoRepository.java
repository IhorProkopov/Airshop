package com.epam.prokopov.shop.repository;

import com.epam.prokopov.shop.model.UserLoginInfo;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserLoginInfoRepository {

    public UserLoginInfo getUserLoginInfo(Connection connection, String email) throws SQLException;

    public void setUserLoginInfo(Connection connection, UserLoginInfo userLoginInfo) throws SQLException;

}
