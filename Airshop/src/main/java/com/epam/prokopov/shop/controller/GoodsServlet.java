package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.model.Product;
import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.service.ProductService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GoodsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService;
    private static final String CREDENTIONALS_MAP = "credentionals";
    private static final String PRODUCT_LIST = "products";
    private static final String GOODS_PAGE = "/goods.jsp";
    private static final String LIST_OF_FILTER_ERRORS = "filterErrors";

    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productService = (ProductService) servletContext.getAttribute("productService");
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        List<Product> products;
        HttpSession session = request.getSession();

        User user = (User) session.getAttribute("user");
        if (user != null) {
            request.setAttribute("user", user);
        }

        Map<String, Object> credentionals = null;
        if (session.getAttribute(CREDENTIONALS_MAP) != null) {
            credentionals = (Map<String, Object>) session.getAttribute(CREDENTIONALS_MAP);
            products = productService.getProductsByCredentionals(credentionals);
            session.removeAttribute(CREDENTIONALS_MAP);
        } else {
            if (session.getAttribute(PRODUCT_LIST) != null) {
                products = (List<Product>) session.getAttribute(PRODUCT_LIST);
            } else {
                products = productService.getAllProducts();
            }
        }


        if (session.getAttribute(LIST_OF_FILTER_ERRORS) != null) {
            request.setAttribute(LIST_OF_FILTER_ERRORS, (List<String>) session.getAttribute(LIST_OF_FILTER_ERRORS));
            session.removeAttribute(LIST_OF_FILTER_ERRORS);
        }


        int productsOnPage;
        if (session.getAttribute("productsonpage") != null) {
            productsOnPage = (int) session.getAttribute("productsonpage");
        } else {
            session.setAttribute("productsonpage", 6);
            productsOnPage = 6;
        }


        int pages = products.size() % productsOnPage == 0 ? products.size() / productsOnPage : products.size() / productsOnPage + 1;
        int page;
        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        } else {
            page = 1;
        }


        request.setAttribute("currentPage", page);
        int lastIndex = productsOnPage * page > products.size() ? products.size() : productsOnPage * page;
        request.setAttribute(PRODUCT_LIST, products.subList(productsOnPage * (page - 1), lastIndex));

        request.setAttribute("pages", pages);
        session.setAttribute(PRODUCT_LIST, products);
        if (session.getAttribute("goodsInCart") == null) {
            session.setAttribute("goodsInCart", 0);
        }
        List<String> categories = productService.getCategories();
        request.setAttribute("categories", categories);
        if (credentionals != null) {
            for (Map.Entry<String, Object> entry : credentionals.entrySet()) {
                if (!entry.getKey().equals("categories") && !entry.getKey().equals("photo")) {
                    request.setAttribute(entry.getKey() + "_holder", entry.getValue());
                }else{
                    if(entry.getKey().equals("photo")){
                    request.setAttribute("photo_holder", "checked");}
                    if(entry.getKey().equals("categories")){
                    request.setAttribute("checkedCategories", (List<String>)entry.getValue());
                    }
                }
            }
        }
        request.getRequestDispatcher(GOODS_PAGE).forward(request, response);
    }

}

