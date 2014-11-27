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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;

class MyError extends Error{

}

class A{
    public static void main(String[] args) {
        LinkedList<Object> ll = new LinkedList<Object>();
        ArrayList<Object> al = new ArrayList<Object>();
        long llTime, alTime;
        long begin = Calendar.getInstance().getTimeInMillis();
        for(int x = 0;x<=1000000;x++){
            ll.add(ll.size()/2, "A");
        }
        llTime = Calendar.getInstance().getTimeInMillis() - begin ;
        begin = Calendar.getInstance().getTimeInMillis();
        for(int x = 0;x<=1000000;x++){
            al.add(al.size()/2, "A");
        }
        alTime = Calendar.getInstance().getTimeInMillis() - begin ;
        System.out.println("Insertion 1_000_000 elements into ArrayList: " + alTime);
        System.out.println("Insertion 1_000_000 elements into LinkedList: " + llTime);
    }



}


public class CartAddingServlet extends HttpServlet {

    private static final String CART = "productsInCart";
    private ProductService productService;

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productService = (ProductService) servletContext.getAttribute("productService");
    }

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int goodsInCart = (int) session.getAttribute("goodsInCart");
        session.setAttribute("goodsInCart", ++goodsInCart);
        Cart cart;
        if (session.getAttribute(CART) != null) {
            cart = (Cart) session.getAttribute(CART);
        } else {
            cart = new Cart();
        }
        Product newProduct = productService.getProductByName(request.getParameter("product"));
        cart.addProduct(newProduct, 1);
        session.setAttribute(CART, cart);
        response.setContentType("application/json");
        response.getWriter().write("{\"count\": " +goodsInCart+", \"total\":"+cart.getSummaryPrice()+" }");
    }

}
