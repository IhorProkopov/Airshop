package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.service.ProductService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FilterServlet extends HttpServlet {

    private final static String GOODS_URL = "/Airshop/goods";

    private ProductService productService;


    public void init() throws ServletException {
        ServletContext servletContext = getServletContext();
        productService = (ProductService) servletContext.getAttribute("productService");
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<String> validationRes = checkPrice(request);
        if (validationRes.size() == 0) {
            Map<String, Object> credendionals = createCredentionalasMap(request);
            session.setAttribute("credentionals", credendionals);
        } else {
            session.setAttribute("filterErrors", validationRes);
        }
        response.sendRedirect(GOODS_URL);
    }

    private Map<String, Object> createCredentionalasMap(HttpServletRequest request) {
        Map<String, Object> credentionals = new HashMap<>();
        if (!request.getParameter("name").equals("")) {
            credentionals.put("name", request.getParameter("name"));
        }
        if (!request.getParameter("country").equals("")) {
            credentionals.put("country", request.getParameter("country"));
        }
        if (!request.getParameter("manufacturer").equals("")) {
            credentionals.put("manufacturer", request.getParameter("manufacturer"));
        }
        if (!request.getParameter("minPrice").equals("")) {
            credentionals.put("minPrice", request.getParameter("minPrice"));
        }
        if (!request.getParameter("maxPrice").equals("")) {
            credentionals.put("maxPrice", request.getParameter("maxPrice"));
        }
        String tempDelivery = request.getParameter("photo");
        if (tempDelivery != null) {
            credentionals.put("photo", true);
        }
        List<String> categories = productService.getCategories();
        List<String> categoriesFromUser = new LinkedList<>();
        for (String category : categories) {
            String tempCategory = request.getParameter(category);
            if (tempCategory != null) {
                categoriesFromUser.add(category);
            }
        }
        if (categoriesFromUser.size() > 0) {
            credentionals.put("categories", categoriesFromUser);
        }
        return credentionals;
    }

    private List<String> checkPrice(HttpServletRequest request) {
        List<String> validationRes = new LinkedList<>();
        boolean isPriceFieldsCorrect = true;
        boolean isMinPriceEmpty = request.getParameter("minPrice").equals("");
        boolean isMaxPriceEmpty = request.getParameter("maxPrice").equals("");
        if (!isMinPriceEmpty && !request.getParameter("minPrice").matches("\\d*")) {
            validationRes.add("Incorrect minimum price");
            isPriceFieldsCorrect = false;
        }
        if (!isMaxPriceEmpty && !request.getParameter("maxPrice").matches("\\d*")) {
            validationRes.add("Incorrect maximum price");
            isPriceFieldsCorrect = false;
        }
        if (isPriceFieldsCorrect && (!isMaxPriceEmpty && !isMinPriceEmpty) && (Long.parseLong(request.getParameter("maxPrice")) < Long.parseLong(request.getParameter("minPrice")))) {
            validationRes.add("Maximum price cannot be less than minimum price");
        }
        return validationRes;
    }

}
