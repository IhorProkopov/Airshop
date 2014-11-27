package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserCheckerServlet extends HttpServlet{

    private UserService userService;



    @Override
    public void init() throws ServletException {
        ServletContext sc = getServletContext();
        userService = (UserService) sc.getAttribute("userService");
    }

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        if(userService.exist(request.getParameter("email"))){
        response.getWriter().write("{\"exist\": true}");
        }else{
            response.getWriter().write("{\"exist\": false}");
        }
    }

}
