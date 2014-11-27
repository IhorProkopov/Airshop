package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.model.*;
import com.epam.prokopov.shop.service.OrderService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BuyingServlet extends HttpServlet {

    private final static String GOODS_URL = "/Airshop/goods";
    OrderService orderService;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        orderService = (OrderService) servletContext.getAttribute("orderService");
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("paymentTypes", PaymentType.values());
        request.getRequestDispatcher("buyingpage.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("productsInCart");
        User user = (User) session.getAttribute("user");
        if (validate(request) && cart != null) {
            String props = request.getParameter("props");
            String delivery = request.getParameter("delivery");
            String payment = request.getParameter("payment");
            String comments = request.getParameter("comments");
            orderService.addOrder(new Order(cart.getProducts(), OrderStatus.SENT, comments, user, delivery, payment, props));
            session.removeAttribute("productsInCart");
            session.setAttribute("goodsInCart", 0);
            response.sendRedirect(GOODS_URL);
            return;
        }
        response.setStatus(response.SC_INTERNAL_SERVER_ERROR);
        request.getRequestDispatcher("/500.jsp").forward(request,
                response);
    }

    private boolean validate(HttpServletRequest request) {
        return request.getParameter("props") != null && request.getParameter("delivery") != null && request.getParameter("payment") != null && request.getParameter("comments") != null;
    }

}
