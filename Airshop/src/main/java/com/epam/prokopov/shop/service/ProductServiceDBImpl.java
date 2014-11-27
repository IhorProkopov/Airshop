package com.epam.prokopov.shop.service;

import com.epam.prokopov.shop.model.Product;
import com.epam.prokopov.shop.repository.DAOException;
import com.epam.prokopov.shop.repository.ProductRepostory;
import com.epam.prokopov.shop.repository.Transaction;
import com.epam.prokopov.shop.repository.TransactionHelper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ProductServiceDBImpl implements ProductService{

    private ProductRepostory productRepostory;
    private TransactionHelper transactionHelper;

    public ProductServiceDBImpl(DataSource dataSource, ProductRepostory productRepostory) {
        this.transactionHelper = new TransactionHelper(dataSource);
        this.productRepostory = productRepostory;
    }
    
    @Override
    public void addProduct(final Product product) throws DAOException {
        transactionHelper.execute(new Transaction() {
            @Override
            public <T> T execute(Connection connection) throws SQLException {
                productRepostory.addProduct(connection, product);
                return null;
            }
        });
    }

    @Override
    public void deleteProductByName(final String name) throws DAOException {
        transactionHelper.execute(new Transaction() {
            @Override
            public <T> T execute(Connection connection) throws SQLException {
                productRepostory.deleteProductByName(connection, name);
                return null;
            }
        });
    }

    @Override
    public void editProduct(final Product product) throws DAOException {
        transactionHelper.execute(new Transaction() {
            @Override
            public <T> T execute(Connection connection) throws SQLException {
                productRepostory.editProduct(connection, product);
                return null;
            }
        });
    }

    @Override
    public Product getProductByName(final String name) throws DAOException {
        return transactionHelper.execute(new Transaction() {
            @Override
            public Product execute(Connection connection) throws SQLException {
                return productRepostory.getProductByName(connection, name);
            }
        });
    }

    @Override
    public List<Product> getAllProducts() throws DAOException {
        return transactionHelper.execute(new Transaction() {
            @Override
            public List<Product> execute(Connection connection) throws SQLException {
                return productRepostory.getAllProducts(connection);
            }
        });
    }

    @Override
    public List<Product> getProductsByCredentionals(final Map<String, Object> credentionals) throws DAOException {
        return transactionHelper.execute(new Transaction() {
            @Override
            public List<Product> execute(Connection connection) throws SQLException {
                return productRepostory.getProductsByCredentionals(connection, credentionals);
            }
        });
    }

    @Override
    public List<String> getCategories() throws DAOException {
        return transactionHelper.execute(new Transaction() {
            @Override
            public List<String> execute(Connection connection) throws SQLException {
                return productRepostory.getCategories(connection);
            }
        });
    }

}
