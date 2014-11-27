package com.epam.prokopov.shop.repository.chain.filter;


import java.util.Map;

public class PhotoFilterLink extends ProductFilterLink {

    private static final String KEY = "photo";

    public PhotoFilterLink(ProductFilterLink nextLink) {
        super(nextLink);
    }

    @Override
    public String editQuery(String query, Map<String, Object> credentionals) {
        if (credentionals.containsKey(KEY)) {
           query = addParameterToQuery(query, credentionals);
        }
        return goToNextLink(query, credentionals);
    }

    @Override
    protected String addParameterToQuery(String query, Map<String, Object> credentionals) {
        String wherePart = (Boolean) credentionals.get(KEY) ? "photo NOT LIKE '%defaultAvatar.jpg'" : "photo LIKE '%defaultAvatar.jpg'";
        return query.replaceAll(";", wherePart + " AND ;");
    }
}
