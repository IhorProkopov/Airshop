package com.epam.prokopov.shop.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.prokopov.shop.service.RegistrationValidator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.epam.prokopov.shop.controller.captcha.CaptchaSaver;
import com.epam.prokopov.shop.service.UserService;

public class RegistrationServletTest {

	private static UserService userService;
	private static ServletContext sc;
	private static CaptchaSaver cs;
	private static ServletConfig config;
	private static RequestDispatcher rd;
	private static RegistrationValidator validator;
	private static HttpServletRequest request;
	private static HttpServletResponse response;
	private static List<String> validationResult;
	private RegistrationServlet servletSpy;

	@BeforeClass
	public static void setUp() {
		validationResult = mock(List.class);
		request = mock(HttpServletRequest.class);
		config = mock(ServletConfig.class);
		response = mock(HttpServletResponse.class);
		sc = mock(ServletContext.class);
		rd = mock(RequestDispatcher.class);
		userService = mock(UserService.class);
		validator = mock(RegistrationValidator.class);
		cs = mock(CaptchaSaver.class);
		when(request.getRequestDispatcher("/registration.jsp")).thenReturn(rd);
		when(config.getServletContext()).thenReturn(sc);
		when(sc.getRequestDispatcher("/registration.jsp")).thenReturn(rd);
		when(sc.getAttribute("userService")).thenReturn(userService);
		when(sc.getAttribute("captchaClass")).thenReturn(cs);
		when(sc.getAttribute("validator")).thenReturn(validator);
		when(sc.getAttribute("javax.servlet.context.tempdir")).thenReturn(
				new File("D:\\"));
		when(request.getHeader("Content-Type"))
				.thenReturn(
						"multipart/form-data; boundary=----WebKitFormBoundaryuFg1Sjl9D2fVwieY");
		when(validator.validate(any(RegistrationDataBean.class))).thenReturn(
				validationResult);
	}

	@Before
	public void initialize() {
		servletSpy = spy(new RegistrationServlet());
		when(servletSpy.getServletConfig()).thenReturn(config);
		when(servletSpy.getServletContext()).thenReturn(sc);
	}

	@Test
	public void acceptTest() throws ServletException, IOException, SQLException {
		when(userService.exist(anyString())).thenReturn(false);

		when(
				cs.isCorrect(any(HttpServletRequest.class), anyString(),
						anyString())).thenReturn(true);
		servletSpy.init(config);
		servletSpy.doPost(request, response);
		verify(response, atLeastOnce()).sendRedirect("/Airshop/goods");
	}

	@Test
	public void incorrectCaptchaTest() throws ServletException, IOException, SQLException {
		when(userService.exist(anyString())).thenReturn(false);
		when(
				cs.isCorrect(any(HttpServletRequest.class), anyString(),
						anyString())).thenReturn(false);

		servletSpy.init(config);
		servletSpy.doPost(request, response);
		verify(rd, atLeastOnce()).forward(request, response);
		verify(validationResult, times(1)).add("Captcha is incorrect");
	}

	@Test
	public void userExistTest() throws ServletException, IOException, SQLException {
		when(userService.exist(anyString())).thenReturn(true);
		when(
				cs.isCorrect(any(HttpServletRequest.class), anyString(),
						anyString())).thenReturn(true);

		servletSpy.init(config);
		servletSpy.doPost(request, response);
		verify(rd, atLeastOnce()).forward(request, response);
		verify(validationResult, times(1)).add("User already exist");
	}

}