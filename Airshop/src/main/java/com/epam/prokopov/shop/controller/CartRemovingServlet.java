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
import java.util.HashMap;

public class CartRemovingServlet extends HttpServlet {
    private static final String CART = "productsInCart";
    private ProductService productService;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productService = (ProductService) servletContext.getAttribute("productService");
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("goodsInCart", 0);
        session.setAttribute(CART, new HashMap<Product, Integer>());
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int goodsInCart = (int) session.getAttribute("goodsInCart");
        int count = -1;
        if (request.getParameter("count") != null) {
            count = Integer.parseInt(request.getParameter("count"));
        }
        int productsToDelete;
        Cart cart;
        if (session.getAttribute(CART) != null) {
            cart = (Cart) session.getAttribute(CART);
        } else {
            return;
        }

        Product newProduct = productService.getProductByName(request.getParameter("product"));
        if(!cart.contains(newProduct)){
            return;
        }
        if (count == -1) {
            productsToDelete = cart.getProducts().get(newProduct);
        } else {
            productsToDelete = count;
        }
        cart.removeProduct(newProduct, count);
        session.setAttribute("goodsInCart", goodsInCart - productsToDelete);
        session.setAttribute(CART, cart);

        response.setContentType("application/json");
        response.getWriter().write("{\"total\":" + cart.getSummaryPrice() + " }");
    }

}
