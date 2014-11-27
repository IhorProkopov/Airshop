package com.epam.prokopov.shop.model;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Order {

    private int id;
    private OrderStatus status;
    private String comments;
    private long time;
    private User user;
    private List<OrderInfo> orderInfos;
    private String deliveryType;
    private String paymentType;
    private String props;

    public Order(Map<Product, Integer> order, OrderStatus status, String comments, User user, String deliveryType, String paymentType, String props){
        this.id = order.hashCode() ;
        this.status = status;
        this.props = props;
        this.paymentType = paymentType;
        this.deliveryType = deliveryType;
        this.comments = comments;
        this.time = Calendar.getInstance().getTimeInMillis();
        this.user = user;
        this.orderInfos = new LinkedList<>();
        for(Map.Entry<Product, Integer> entry : order.entrySet()){
            orderInfos.add(new OrderInfo(entry.getKey().getName(), entry.getValue(), entry.getKey().getPrice()));
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderInfo> getOrderInfos() {
        return orderInfos;
    }

    public void setOrderInfos(List<OrderInfo> orderInfos) {
        this.orderInfos = orderInfos;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

}
