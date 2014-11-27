package com.epam.prokopov.shop.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;

import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.model.UserRoles;
import com.epam.prokopov.shop.repository.UserRepositoryImpl;
import com.epam.prokopov.shop.service.UserService;
import com.epam.prokopov.shop.service.UserServiceImpl;

public class UserServiceTest {

	private static Map<String, User> users;
	private static UserService userService; 
	
	@BeforeClass
	public static void setUp(){
		users = new HashMap<String, User>();
		users.put("tw@gmail.com", new User("Tom", "Watson", "tw@gmail.com",
				"123qweQWE", false, null, UserRoles.USER));
		users.put("th@gmail.com", new User("Tom", "Hanks", "th@gmail.com",
				"123qweQWE", true, null, UserRoles.USER));
		userService = new UserServiceImpl(new UserRepositoryImpl(users));
	}
	
	@Test
	public void correctCredentionalsTest() throws SQLException {
		Assert.assertTrue(userService.checkPassword("tw@gmail.com", "123qweQWE"));
	}
	
	@Test
	public void incorrectPasswordTest() throws SQLException {
		Assert.assertFalse(userService.checkPassword("tw@gmail.com", "123123qweQWE"));
	}
	
	@Test
	public void incorrectEmailTest() throws SQLException {
		Assert.assertFalse(userService.checkPassword("tw123@gmail.com", "123qweQWE"));
	}

}
