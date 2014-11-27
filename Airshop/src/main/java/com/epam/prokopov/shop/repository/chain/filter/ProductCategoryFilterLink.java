package com.epam.prokopov.shop.repository.chain.filter;

import java.util.List;
import java.util.Map;

public class ProductCategoryFilterLink extends ProductFilterLink{

    private static final String KEY = "categories";

    public ProductCategoryFilterLink(ProductFilterLink nextLink) {
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
        List<String> categories = (List<String>)credentionals.get(KEY);
        StringBuilder wherePart = new StringBuilder();
        wherePart.append("( ");
        for(String category : categories){
            wherePart.append("productCategory = '" + category + "' OR ");
        }
        wherePart.delete(wherePart.length()-4, wherePart.length());
        wherePart.append(") ");
        return query.replaceAll(";", wherePart.toString() + " AND ;");
    }

}
