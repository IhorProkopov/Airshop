package com.epam.prokopov.shop.service;

import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.model.UserLoginInfo;
import com.epam.prokopov.shop.repository.DAOException;

public interface UserService {

    public void addNewUser(User user) throws DAOException;

    public boolean exist(String email) throws DAOException;

    public void removeUser(String email) throws DAOException;

    public void editUser(User user, UserLoginInfo userLoginInfo) throws DAOException;

    public User getUser(String email) throws DAOException;

    public boolean checkPassword(String email, String password) throws DAOException;

}
