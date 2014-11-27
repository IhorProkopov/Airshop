package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.model.UserLoginInfo;
import com.epam.prokopov.shop.service.UserLoginInfoService;
import com.epam.prokopov.shop.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final static String GOODS_URL = "/Airshop/goods";
    private UserService userService;
    private UserLoginInfoService userLoginInfoService;
    private final static String LOGIN_PAGE = "/loginpage.jsp";
    private final static String TARGET_PAGE = "targetPage";
    private final static String BANNED = "banned";


    @Override
    public void init() throws ServletException {
        ServletContext sc = getServletContext();
        userService = (UserService) sc.getAttribute("userService");
        userLoginInfoService = (UserLoginInfoService) sc.getAttribute("userLoginInfoService");
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(LOGIN_PAGE).forward(request,
                response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        String login = (String) request.getParameter("login");
        String password = (String) request.getParameter("password");
        HttpSession session = request.getSession();
        try {
            UserLoginInfo userLoginInfo = userLoginInfoService.getUserLoginInfo(login);
            if (userService.checkPassword(login, password)) {
                userLoginInfo.setTries(0);
                if (userLoginInfo.getTimeOfTheEnd() < Calendar.getInstance().getTimeInMillis()) {
                    userLoginInfo.setLastVisitTime(Calendar.getInstance().getTimeInMillis());
                    User user = userService.getUser(login);
                    session.setAttribute("user", user);
                    String targetPage = (String) session.getAttribute(TARGET_PAGE);
                    session.removeAttribute(TARGET_PAGE);
                    if (targetPage == null) {
                        response.sendRedirect(GOODS_URL);
                    } else {
                        response.sendRedirect(targetPage);
                    }
                } else {
                    request.setAttribute(BANNED, true);
                    request.getRequestDispatcher(LOGIN_PAGE).forward(request,
                            response);
                }
            } else {
                userLoginInfo.setTries(userLoginInfo.getTries() + 1);
                if (userLoginInfo.getTimeOfTheEnd() < Calendar.getInstance().getTimeInMillis()) {
                    if (userLoginInfo.getTries() < 5) {
                        request.setAttribute("incorrect_credentionals", true);
                    } else {
                        userLoginInfo.setTimeOfTheEnd(Calendar.getInstance().getTimeInMillis() + 1800000);
                        request.setAttribute(BANNED, true);
                    }
                } else {
                    request.setAttribute(BANNED, true);
                }sta
                request.getRequestDispatcher(LOGIN_PAGE).forward(request,
                        response);

            }
            userLoginInfoService.setUserLoginInfo(userLoginInfo);
        } catch (Exception e) {
            response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
            request.getRequestDispatcher("/500.jsp").forward(request,
                    response);
        }
    }

}
