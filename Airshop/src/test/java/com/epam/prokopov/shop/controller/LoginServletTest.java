package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.model.UserLoginInfo;
import com.epam.prokopov.shop.model.UserRoles;
import com.epam.prokopov.shop.service.UserLoginInfoService;
import com.epam.prokopov.shop.service.UserService;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class LoginServletTest {
    private static ServletConfig config;
    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static HttpSession session;
    private static ServletContext sc;
    private static RequestDispatcher rd;
    private static UserService userService;
    private static UserLoginInfoService userLoginInfoService;

    @BeforeClass
    public static void setUp() {
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setTries(0);
        userLoginInfo.setTimeOfTheEnd(0);
        userLoginInfo.setLastVisitTime(0);
        userLoginInfo.setLogin("bla@bls.bal");
        userLoginInfo.setPass("123qweQWE");
        userLoginInfo.setRole(UserRoles.USER);
        config = mock(ServletConfig.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        sc = mock(ServletContext.class);
        rd = mock(RequestDispatcher.class);
        userService = mock(UserService.class);
        userLoginInfoService = mock(UserLoginInfoService.class);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn("bla@bls.bal");
        when(request.getRequestDispatcher(anyString())).thenReturn(rd);
        when(request.getParameter("password")).thenReturn("123qweQWE");
        when(sc.getAttribute("userService")).thenReturn(userService);
        when(sc.getAttribute("userLoginInfoService")).thenReturn(userLoginInfoService);
        when(session.getServletContext()).thenReturn(sc);
        when(sc.getRequestDispatcher("/login.jsp")).thenReturn(rd);
        when(userLoginInfoService.getUserLoginInfo("bla@bls.bal")).thenReturn(userLoginInfo);
    }

    @Test
    public void correctCredentionalsTest() throws ServletException, IOException, SQLException {
        LoginServlet log = new LoginServlet();
        LoginServlet servletSpy = spy(log);
   when(servletSpy.getServletConfig()).thenReturn(config);
        when(servletSpy.getServletContext()).thenReturn(sc);
        when(userService.checkPassword("bla@bls.bal", "123qweQWE")).thenReturn(true);
        servletSpy.init(config);
        servletSpy.doPost(request, response);
        verify(response).sendRedirect("/Airshop/goods");
    }

    @Test
    public void incorrectPassOrEmailTest() throws ServletException, IOException, SQLException {
        LoginServlet log = new LoginServlet();
        LoginServlet servletSpy = spy(log);
        when(servletSpy.getServletConfig()).thenReturn(config);
        when(servletSpy.getServletContext()).thenReturn(sc);
        when(userService.checkPassword("bla@bls.bal", "123qweQWE")).thenReturn(false);
        servletSpy.init(config);
        servletSpy.doPost(request, response);
        verify(request).getRequestDispatcher("/loginpage.jsp");
        verify(rd).forward(request, response);
    }

}