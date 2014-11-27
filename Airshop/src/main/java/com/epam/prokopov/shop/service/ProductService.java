package com.epam.prokopov.shop.service;

import com.epam.prokopov.shop.model.Product;
import com.epam.prokopov.shop.repository.DAOException;

import java.util.List;
import java.util.Map;

public interface ProductService {

    public void addProduct(Product product) throws DAOException;

    public void deleteProductByName(String name) throws DAOException;

    public void editProduct(Product product) throws DAOException;

    public Product getProductByName(String name) throws DAOException;

    public List<Product> getAllProducts() throws DAOException;

    public List<Product> getProductsByCredentionals(Map<String, Object> credentionals) throws DAOException;

    public List<String> getCategories() throws DAOException;

}
