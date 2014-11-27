package com.epam.prokopov.shop.repository;

import com.epam.prokopov.shop.model.Product;
import java.sql.Connection;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface ProductRepostory {

    public void addProduct(Connection connection, Product product) throws SQLException;

    public void deleteProductByName(Connection connection, String name) throws SQLException;

    public void editProduct(Connection connection, Product product) throws SQLException;

    public Product getProductByName(Connection connection, String name) throws SQLException;

    public List<Product> getAllProducts(Connection connection) throws SQLException;

    public List<Product> getProductsByCredentionals(Connection connection, Map<String, Object> credentionals) throws SQLException;

    public List<String> getCategories(Connection connection) throws SQLException;

}
