package com.epam.prokopov.shop.service;

import java.util.ArrayList;
import java.util.List;

import com.epam.prokopov.shop.controller.RegistrationDataBean;

public class RegistrationValidator {

	private static final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String passPattrn = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,}";
	private static final String namePattern = "^[a-zA-Z_-]{2,}$";

	public List<String> validate(RegistrationDataBean rb) {

		List<String> res = new ArrayList<String>();

		if (rb.getName().equals("") || rb.getName().length() > 20
				|| !rb.getName().matches(namePattern)) {
			res.add("Incorrect name");
		}

		if (rb.getSurname().equals("") || rb.getSurname().length() > 20
				|| !rb.getSurname().matches(namePattern)) {
			res.add("Incorrect surname");
		}

		if (!rb.getPass().matches(passPattrn)) {
			res.add("Incorrect password");
		}

		if (!rb.getConfPass().matches(passPattrn)) {
			res.add("Incorrect password confirmation");
		}

		if (!rb.getPass().equals(rb.getConfPass())) {
			res.add("Passwords are not equals");
		}

		if (rb.getEmail().equals("") || !rb.getEmail().matches(emailPattern)) {
			res.add("Incorrect email");
		}
		return res;
	}
}
