package com.epam.prokopov.shop.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private Map<Product, Integer> bascet;

    public Cart() {
        bascet = new HashMap<>();
    }

    public void addProduct(Product product, int count) {
        if (bascet.containsKey(product)) {
            bascet.put(product, bascet.get(product) + count);
        } else {
            bascet.put(product, count);
        }
    }

    public void removeProduct(Product product, int count) {
        if (bascet.get(product) == count || count == -1) {
            bascet.remove(product);
        } else {
            if (bascet.get(product) > count) {
                bascet.put(product, bascet.get(product) - count);
            }
        }
    }

    public Map<Product, Integer> getProducts() {
        return Collections.unmodifiableMap(bascet);
    }

    public int getCount() {
        int count = 0;
        for(Map.Entry<Product, Integer> entry : bascet.entrySet()){
            count+=entry.getValue();
        }
        return count;
    }

    public int getSummaryPrice() {
        int res = 0;
        for (Map.Entry<Product, Integer> entry : bascet.entrySet()) {
            res += entry.getKey().getPrice() * entry.getValue();
        }
        return res;
    }

    public boolean contains(Product prouct) {
        return bascet.containsKey(prouct);
    }

    public void setProductCount(Product product, int count) {
        if (bascet.containsKey(product)) {
            bascet.put(product, count);
        }
    }

}
