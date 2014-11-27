package com.epam.prokopov.shop.repository;

import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.model.UserLoginInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {

	private Map<String, User> users;

	public UserRepositoryImpl(Map<String, User> users) {
		this.users = users;
	}

	@Override
	public void addNewUser(Connection connection,User user) {
		users.put(user.getEmail(), user);
	}

	@Override
	public boolean exist(Connection connection,String email) {
		return users.containsKey(email);
	}

    @Override
    public void removeUser(Connection connection, String email) throws SQLException {

    }

    @Override
    public void editUser(Connection connection, User user, UserLoginInfo userLoginInfo) throws SQLException {

    }

	@Override
	public User getUser(Connection connection, String email) {
		return users.get(email);
	}

}
