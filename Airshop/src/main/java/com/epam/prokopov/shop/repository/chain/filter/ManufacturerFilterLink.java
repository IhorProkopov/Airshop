package com.epam.prokopov.shop.repository.chain.filter;

import java.util.Map;

public class ManufacturerFilterLink extends ProductFilterLink{

    private static final String KEY = "manufacturer";

    public ManufacturerFilterLink(ProductFilterLink nextLink) {
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
        String wherePart = "manufacturer LIKE '%" + credentionals.get(KEY) + "%'";
        return query.replaceAll(";", wherePart + " AND ;");
    }

}
