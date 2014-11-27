package com.epam.prokopov.shop.model;

public class OrderInfo {

    private String product;
    private int count;
    private long price;

    public OrderInfo() {
    }

    public OrderInfo(String product, int count, long price) {
        this.product = product;
        this.count = count;
        this.price = price;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
