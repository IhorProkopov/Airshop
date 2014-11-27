package com.epam.prokopov.shop.repository.chain.filter;


import java.util.Map;

public abstract class ProductFilterLink {

    private static final String AND_FINISH =" AND ;";
    private static final String WHERE_FINISH =" WHERE ;";

    public ProductFilterLink(ProductFilterLink nextLink){
        this.next = nextLink;
    }

    protected ProductFilterLink next;

    public abstract String editQuery(String query, Map<String, Object> credentionals);

    protected abstract String addParameterToQuery(String query, Map<String, Object> credentionals);

    protected String finishQuery(String query){
        if(query.contains(AND_FINISH)){
            return query.replace(AND_FINISH, ";");
        }else{
            if(query.contains(WHERE_FINISH)){
                return query.replace(WHERE_FINISH, ";");
            }
        }
        return query;
    }

    protected String goToNextLink(String query, Map<String, Object> credentionals){
        if (next != null) {
            query = next.editQuery(query, credentionals);
        } else {
            query = finishQuery(query);
        }
        return query;
    }

}
