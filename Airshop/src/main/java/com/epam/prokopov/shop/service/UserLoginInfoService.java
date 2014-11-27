package com.epam.prokopov.shop.service;


import com.epam.prokopov.shop.model.UserLoginInfo;
import com.epam.prokopov.shop.repository.DAOException;

public interface UserLoginInfoService {

    public UserLoginInfo getUserLoginInfo(String email) throws DAOException;

    public void setUserLoginInfo(UserLoginInfo userLoginInfo) throws DAOException;

}
