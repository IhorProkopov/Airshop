package com.epam.prokopov.shop.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;

import com.epam.prokopov.shop.service.RegistrationValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class ValidatorTest {

	private static RegistrationDataBean rb;
	private static List<String> list;
	private String name;
	private String surname;
	private String email;
	private String pass;
	private String confPass;
	private String captcha;
	private int res;

	public ValidatorTest(String name, String surname, String email,
			String pass, String confPass, int res, String captcha) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.pass = pass;
		this.confPass = confPass;
		this.res = res;
		this.captcha = captcha;
	}

	@Parameterized.Parameters
	public static Collection primeNumbers() {
		return Arrays
				.asList(new Object[][] {
						{ "name", "suname", "eml@lkj.con", "123qweQWE",
								"123qweQWE", 0, "123456" },
						{ "name123", "suname", "eml@lkj.con", "123qweQWE",
								"123qweQWE", 1, "123456" },
						{ "n", "suname", "eml@lkj.con", "123qweQWE",
								"123qweQWE", 1, "123456" },
						{
								"njdslkjgdskjghdkjghdsghsdghsdghdslfghsdkjghdslkjghsldfkjgamdgdgdgdg",
								"suname", "eml@lkj.con", "123qweQWE",
								"123qweQWE", 1, "123456" },
						{ "name", "name123", "eml@lkj.con", "123qweQWE",
								"123qweQWE", 1, "123456" },
						{ "name", "n", "eml@lkj.con", "123qweQWE", "123qweQWE",
								1, "123456" },
						{
								"name",
								"njdslkjgdskjghdkjghdsghsdghsdghdslfghsdkjghdslkjghsldfkjgamdgdgdgdg",
								"eml@lkj.con", "123qweQWE", "123qweQWE", 1,
								"123456" },
						{ "name", "suname", "emaildk", "123qweQWE",
								"123qweQWE", 1, "123456" },
						{ "name", "suname", "emaild@kdgdgfgd", "123qweQWE",
								"123qweQWE", 1, "123456" },
						{ "name", "suname", "emaildksdvs.com", "123qweQWE",
								"123qweQWE", 1, "123456" },
						{ "name", "suname", "eml@lkj.con", "sdfghsdf",
								"123qweQWE", 2, "123456" },
						{ "name", "suname", "eml@lkj.con", "sdkj234234lgdf",
								"123qweQWE", 2, "123456" },
						{ "name", "suname", "eml@lkj.con", "sdkjlFDFDgdf",
								"123qweQWE", 2, "123456" },
						{ "name", "suname", "eml@lkj.con", "sF3", "123qweQWE",
								2, "123456" },
						{ "name", "suname", "eml@lkj.con", "123qweQWE",
								"sdfghsdf", 2, "123456" },
						{ "name", "suname", "eml@lkj.con", "123qweQWE",
								"sdkj234234lgdf", 2, "123456" },
						{ "name", "suname", "eml@lkj.con", "123qweQWE",
								"sdkjlFDFDgdf", 2, "123456" },
						{ "name", "suname", "eml@lkj.con", "123qweQWE", "sF3",
								2, "123456" },
						{ "name", "suname", "eml@lkj.con", "123qweQWE",
								"123qrtyweQWE", 1, "123456" } });

	}

	@Before
	public void initialize() {
		rb = new RegistrationDataBean(name, surname, pass, confPass, email,
				captcha, false, null);
	}

	@Test
	public void validationTest() throws ServletException, IOException {
		RegistrationValidator v = new RegistrationValidator();
		list = v.validate(rb);
		Assert.assertTrue(list.size() == res);
	}

}
