package com.epam.prokopov.shop.repository;

import com.epam.prokopov.shop.model.Order;
import com.epam.prokopov.shop.model.OrderInfo;
import com.epam.prokopov.shop.model.OrderStatus;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderRepository {

    public void addOrder(Connection connection, Order order) throws SQLException;

    public void removeOrder(Connection connection, int orderId) throws SQLException;

    public void editOrderStatus(Connection connection, int orderId, OrderStatus orderStatus) throws SQLException;

    public List<OrderInfo> getOrders(Connection connection, String email) throws SQLException;

    public List<OrderInfo> getOrders(Connection connection, Long startTime, Long endTime) throws SQLException;

}
