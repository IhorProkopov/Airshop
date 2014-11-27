package com.epam.prokopov.shop.controller;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResetSortingServlet extends HttpServlet{

    private static final String GOODS_SERVLET = "/Airshop/goods";

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.getSession().removeAttribute("products");
        response.sendRedirect(GOODS_SERVLET);
    }

}
