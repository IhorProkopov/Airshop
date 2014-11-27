package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.model.Product;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortingServlet extends HttpServlet {

    private static final String PRODUCTS_ON_PAGE = "productsonpage";
    private static final String GOODS_SERVLET = "/Airshop/goods";
    private static final int DEFAULT_PRODUCTS_COUNT_ON_PAGE = 6;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        List<Product> products = (List<Product>) request.getSession().getAttribute("products");
        String sortType = request.getParameter("sort");
        if (sortType != null) {
            switch (sortType) {
                case "name":
                    Collections.sort(products);
                    break;
                case "namereverse":
                    Collections.sort(products, Collections.reverseOrder());
                    break;
                case "price":
                    Collections.sort(products, new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o1.getPrice().compareTo(o2.getPrice());
                        }
                    });
                    break;
                case "pricereverse":
                    Collections.sort(products, new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return o2.getPrice().compareTo(o1.getPrice());
                        }
                    });
                    break;
            }
            HttpSession session = request.getSession();
            session.setAttribute("products", products);
        }
        int productsOnPage;
        if (request.getParameter(PRODUCTS_ON_PAGE) != null) {
            productsOnPage = Integer.parseInt(request.getParameter(PRODUCTS_ON_PAGE));
        } else {
            productsOnPage = DEFAULT_PRODUCTS_COUNT_ON_PAGE;
        }
        request.getSession().setAttribute(PRODUCTS_ON_PAGE, productsOnPage);
        response.sendRedirect(GOODS_SERVLET);
    }

}
