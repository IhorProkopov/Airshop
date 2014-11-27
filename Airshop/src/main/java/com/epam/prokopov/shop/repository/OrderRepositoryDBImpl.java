package com.epam.prokopov.shop.repository;

import com.epam.prokopov.shop.model.*;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class OrderRepositoryDBImpl implements OrderRepository {

    private static final String ADD_ORDER_TABLE_QUERY = "INSERT INTO order_table (status, comments, time, user_email, props, delivery, payment) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String ADD_ORDER_INFO_QUERY = "INSERT INTO order_info (product_name, price, count, order_id) VALUES (?, ?, ?, ?)";
    private static final String DELETE_ORDER_TABLE_QUERY = "DELETE FROM order_table WHERE id = ?;";
    private static final String DELETE_ORDER_INFO_QUERY = "DELETE FROM order_info WHERE order_id = ?;";
    private static final String EDIT_ORDER_STATUS = "UPDATE order_table SET status = ? WHERE id = ?;";
    private static final String GET_ORDERS_BY_USER = "SELECT order_info.* FROM  order_info WHERE order_info.order_id = (SELECT order_table.id FROM  order_table WHERE order_table.user_email = ?) GROUP BY order_info.product_name;";
    private static final String GET_ORDERS_BY_TIME = "SELECT order_info.* FROM  order_info WHERE order_info.order_id = (SELECT order_table.id FROM  order_table WHERE order_table.time BETWEEN ? AND ?) GROUP BY order_info.product_name;";

    @Override
    public void addOrder(Connection connection, Order order) throws SQLException {
        int orderId = 0;
        try (PreparedStatement statement = connection.prepareStatement(ADD_ORDER_TABLE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;

            statement.setString(++i, order.getStatus().toString());
            statement.setString(++i, order.getComments());
            statement.setLong(++i, order.getTime());
            statement.setString(++i, order.getUser().getEmail());
            statement.setString(++i, order.getProps());
            statement.setString(++i, order.getDeliveryType());
            statement.setString(++i, order.getPaymentType());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            }
        }

        List<OrderInfo> orderInfos = order.getOrderInfos();
        for (OrderInfo orderInfo : orderInfos) {
            try (PreparedStatement statement = connection.prepareStatement(ADD_ORDER_INFO_QUERY)) {
                int i = 0;

                statement.setString(++i, orderInfo.getProduct());
                statement.setLong(++i, orderInfo.getPrice());
                statement.setInt(++i, orderInfo.getCount());
                statement.setInt(++i, orderId);

                statement.executeUpdate();
            }
        }
    }

    @Override
    public void removeOrder(Connection connection, int orderId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ORDER_TABLE_QUERY)) {
            statement.setInt(1, orderId);

            statement.executeUpdate();
        }

        try (PreparedStatement statement = connection.prepareStatement(DELETE_ORDER_INFO_QUERY)) {
            statement.setInt(1, orderId);

            statement.executeUpdate();
        }
    }


    @Override
    public void editOrderStatus(Connection connection, int orderId, OrderStatus orderStatus) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(EDIT_ORDER_STATUS)) {
            statement.setString(1, orderStatus.toString());
            statement.setInt(2, orderId);

            statement.executeUpdate();
        }
    }

    @Override
    public List<OrderInfo> getOrders(Connection connection, String email) throws SQLException {
        List<OrderInfo> orderInfos = new LinkedList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ORDERS_BY_USER)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setCount(rs.getInt("count"));
                orderInfo.setPrice(rs.getInt("price"));
                orderInfo.setProduct(rs.getString("product_name"));
                orderInfos.add(orderInfo);
            }
            return orderInfos;
        }
    }

    @Override
    public List<OrderInfo> getOrders(Connection connection, Long startTime, Long endTime) throws SQLException {
        List<OrderInfo> orderInfos = new LinkedList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_ORDERS_BY_TIME)) {
            statement.setLong(1, startTime);
            statement.setLong(2, endTime);
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                OrderInfo orderInfo = new OrderInfo();
                orderInfo.setCount(rs.getInt("count"));
                orderInfo.setPrice(rs.getInt("price"));
                orderInfo.setProduct(rs.getString("product_name"));
                orderInfos.add(orderInfo);
            }
            return orderInfos;
        }
    }
}
