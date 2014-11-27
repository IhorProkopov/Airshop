package com.epam.prokopov.shop.repository.chain.filter;

import java.util.Map;

public class PriceFilterLink extends ProductFilterLink {

    private static final String KEY_MIN = "minPrice";
    private static final String KEY_MAX = "maxPrice";

    public PriceFilterLink(ProductFilterLink nextLink) {
        super(nextLink);
    }

    @Override
    public String editQuery(String query, Map<String, Object> credentionals) {
        if (credentionals.containsKey(KEY_MIN) || credentionals.containsKey(KEY_MAX)) {
            query = addParameterToQuery(query, credentionals);
        }
        return goToNextLink(query, credentionals);
    }

    @Override
    protected String addParameterToQuery(String query, Map<String, Object> credentionals) {
        String lessWherePart = credentionals.containsKey(KEY_MAX) ? "price <= '" + credentionals.get(KEY_MAX) + "'" : null;
        String moreWherePart = credentionals.containsKey(KEY_MIN) ? "price >= '" + credentionals.get(KEY_MIN) + "'" : null;
        String res = lessWherePart != null ? query.replaceAll(";", lessWherePart + " AND ;") : query;
        res = moreWherePart != null ? res.replaceAll(";", moreWherePart + " AND ;") : res;
        return res;
    }

}
