package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.model.Cart;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CartServlet extends HttpServlet {

    private static final String CART = "productsInCart";
    private static final String CART_PAGE = "/cart.jsp";

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int total = 0;
        if(session.getAttribute(CART)!=null){
            Cart cart = (Cart) session.getAttribute(CART);
            total = cart.getSummaryPrice();
        }
        request.setAttribute("total", total);
        request.getRequestDispatcher(CART_PAGE).forward(request, response);
    }

}
