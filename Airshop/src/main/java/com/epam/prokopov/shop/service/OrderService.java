package com.epam.prokopov.shop.service;

import com.epam.prokopov.shop.model.Order;
import com.epam.prokopov.shop.model.OrderInfo;
import com.epam.prokopov.shop.model.OrderStatus;
import com.epam.prokopov.shop.repository.DAOException;

import java.util.List;

public interface OrderService {

    public void addOrder(Order order) throws DAOException;

    public void removeOrder(int orderId) throws DAOException;

    public void editOrderStatus(int orderId, OrderStatus orderStatus) throws DAOException;

    public List<OrderInfo> getOrders(String email) throws DAOException;

    public List<OrderInfo> getOrders(Long startTime, Long endTime) throws DAOException;

}
