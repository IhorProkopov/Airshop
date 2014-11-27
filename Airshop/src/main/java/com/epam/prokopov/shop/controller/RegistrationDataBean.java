package com.epam.prokopov.shop.controller;

public class RegistrationDataBean {	

	private String name;
	private String surname;
	private String pass;
	private String confPass;
	private String email;
	private String userCaptcha;
	private boolean delivery;
	private String photo;

	public RegistrationDataBean() {
	}

	public RegistrationDataBean(String name, String surname, String pass,
			String confPass, String email, String userCaptcha,
			boolean delivery, String photo) {
		this.name = name;
		this.surname = surname;
		this.pass = pass;
		this.confPass = confPass;
		this.email = email;
		this.userCaptcha = userCaptcha;
		this.delivery = delivery;
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPass() {
		return pass;
	}

	public String getConfPass() {
		return confPass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setConfPass(String confPass) {
		this.confPass = confPass;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserCaptcha() {
		return userCaptcha;
	}

	public void setUserCaptcha(String userCaptcha) {
		this.userCaptcha = userCaptcha;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isDelivery() {
		return delivery;
	}

	public void setDelivery(boolean delivery) {
		this.delivery = delivery;
	}

}
