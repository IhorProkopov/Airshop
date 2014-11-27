package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.model.Cart;
import com.epam.prokopov.shop.model.Product;
import com.epam.prokopov.shop.service.ProductService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CartSetServlet extends HttpServlet {

    private static final String CART = "productsInCart";
    private ProductService productService;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productService = (ProductService) servletContext.getAttribute("productService");
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(CART);

        Product newProduct = productService.getProductByName(request.getParameter("product"));
        int newCount;
        try {
            newCount = Integer.parseInt(request.getParameter("count"));
        } catch (Exception e) {
            return;
        }
        cart.setProductCount(newProduct, newCount);
        session.setAttribute(CART, cart);
        session.setAttribute("goodsInCart", cart.getCount());
        response.setContentType("application/json");
        response.getWriter().write("{\"total\":" + cart.getSummaryPrice() + " }");
    }

}
