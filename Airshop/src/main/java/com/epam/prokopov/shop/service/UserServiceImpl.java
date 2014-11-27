package com.epam.prokopov.shop.service;

import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.model.UserLoginInfo;
import com.epam.prokopov.shop.repository.DAOException;
import com.epam.prokopov.shop.repository.UserRepository;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void addNewUser(User user) {
        try {
            userRepository.addNewUser(null, user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(String email) {
        try {
            return userRepository.getUser(null, email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean exist(String email) {
        try {
            return userRepository.exist(null, email);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void removeUser(String email) throws DAOException {
        try {
            userRepository.removeUser(null, email);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public void editUser(User user, UserLoginInfo userLoginInfo) throws DAOException {
        try {
            userRepository.editUser(null, user, userLoginInfo);
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public boolean checkPassword(String email, String password) {
        User u;
        try {
            u = userRepository.getUser(null, email);

            if (u != null) {
                if (u.getPass().equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
