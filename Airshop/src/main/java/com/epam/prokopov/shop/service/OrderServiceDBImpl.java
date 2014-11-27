package com.epam.prokopov.shop.service;

import com.epam.prokopov.shop.model.Order;
import com.epam.prokopov.shop.model.OrderInfo;
import com.epam.prokopov.shop.model.OrderStatus;
import com.epam.prokopov.shop.repository.DAOException;
import com.epam.prokopov.shop.repository.OrderRepository;
import com.epam.prokopov.shop.repository.Transaction;
import com.epam.prokopov.shop.repository.TransactionHelper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceDBImpl implements OrderService{

    private OrderRepository orderRepository;
    private TransactionHelper transactionHelper;

    public OrderServiceDBImpl(DataSource dataSource, OrderRepository orderRepository) {
        this.transactionHelper = new TransactionHelper(dataSource);
        this.orderRepository = orderRepository;
    }

    @Override
    public void addOrder(final Order order) throws DAOException {
        transactionHelper.execute(new Transaction() {
            @Override
            public <T> T execute(Connection connection) throws SQLException {
                orderRepository.addOrder(connection, order);
                return null;
            }
        });
    }

    @Override
    public void removeOrder(final int orderId) throws DAOException {
        transactionHelper.execute(new Transaction() {
            @Override
            public <T> T execute(Connection connection) throws SQLException {
                orderRepository.removeOrder(connection, orderId);
                return null;
            }
        });
    }

    @Override
    public void editOrderStatus(final int orderId, final OrderStatus orderStatus) throws DAOException {
            transactionHelper.execute(new Transaction() {
                @Override
                public <T> T execute(Connection connection) throws SQLException {
                    orderRepository.editOrderStatus(connection, orderId, orderStatus);
                    return null;
                }
            });
    }

    @Override
    public List<OrderInfo> getOrders(final String email) throws DAOException {
        return transactionHelper.execute(new Transaction() {
            @Override
            public List<OrderInfo> execute(Connection connection) throws SQLException {
                return orderRepository.getOrders(connection, email);
            }
        });
    }

    @Override
    public List<OrderInfo> getOrders(final Long startTime, final Long endTime) throws DAOException {
        return transactionHelper.execute(new Transaction() {
            @Override
            public List<OrderInfo> execute(Connection connection) throws SQLException {
                return orderRepository.getOrders(connection, startTime, endTime);
            }
        });
    }
}
