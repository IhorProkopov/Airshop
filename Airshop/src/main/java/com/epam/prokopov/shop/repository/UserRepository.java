package com.epam.prokopov.shop.repository;

import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.model.UserLoginInfo;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserRepository {

    public void addNewUser(Connection connection, User user) throws SQLException;

    public boolean exist(Connection connection, String email) throws SQLException;

    public void removeUser(Connection connection, String email) throws SQLException;

    public void editUser(Connection connection, User user, UserLoginInfo userLoginInfo) throws SQLException;

    public User getUser(Connection connection, String email) throws SQLException;

}
