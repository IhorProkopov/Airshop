package com.epam.prokopov.shop.model;

public class User {

	private String name;
	private String surname;
	private String email;
	private String pass;
	private boolean delivery;
	private String photo;
	private UserRoles role;

	public User() {
	}

	public User(String name, String surname, String email, String pass,
			boolean delivery, String photo, UserRoles role) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.pass = pass;
		this.delivery = delivery;
		this.photo = photo;
		this.role = role;
	}

	public UserRoles getRole() {
		return role;
	}

	public void setRole(UserRoles role) {
		this.role = role;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setDelivery(boolean delivery) {
		this.delivery = delivery;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	public String getPass() {
		return pass;
	}

	public boolean getDelivery() {
		return delivery;
	}

	public String getPhoto() {
		return photo;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != String.class || obj == null) {
			return false;
		}
		if (email.equals(this.email)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return email.hashCode();
	}
	
}
