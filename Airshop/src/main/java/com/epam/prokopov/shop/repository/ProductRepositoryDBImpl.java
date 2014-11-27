package com.epam.prokopov.shop.repository;

import com.epam.prokopov.shop.model.Product;
import com.epam.prokopov.shop.model.ProductCategory;
import com.epam.prokopov.shop.repository.chain.filter.*;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProductRepositoryDBImpl implements ProductRepostory {

    private static final String INSERT_PRODUCT_QUERY = "INSERT INTO product (name, price, description, productCategory, manufacturer, country, photo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_PRODUCT = "UPDATE product SET name = ?, price = ?, description = ?, productCategory = ?, manufacturer = ?, country = ?, photo = ? WHERE name = ?;";
    private static final String DELETE_PRODUCT_QUERY = "DELETE FROM product WHERE product = ?;";
    private static final String GET_ALL_PRODUCTS = "SELECT * FROM product;";
    private static final String GET_PRODUCTS_BY_CREDENTIONALS = "SELECT * FROM product WHERE ;";
    private static final String GET_PRODUCT = "SELECT * FROM product WHERE name = ?;";
    private static final String GET_CATEGORIES = "SELECT productCategory from product GROUP BY productCategory;";

    @Override
    public void addProduct(Connection connection, Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PRODUCT_QUERY)) {
            int i = 0;

            statement.setString(++i, product.getName());
            statement.setLong(++i, product.getPrice());
            statement.setString(++i, product.getDescription());
            statement.setString(++i, product.getProductCategory().toString());
            statement.setString(++i, product.getManufacturer());
            statement.setString(++i, product.getCountry());
            statement.setString(++i, product.getPhoto());

            statement.executeUpdate();
        }
    }

    @Override
    public void deleteProductByName(Connection connection, String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PRODUCT_QUERY)) {
            statement.setString(1, name);
            statement.executeUpdate();
        }
    }

    @Override
    public void editProduct(Connection connection, Product product) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT)) {
            int i = 0;

            statement.setString(++i, product.getName());
            statement.setLong(++i, product.getPrice());
            statement.setString(++i, product.getDescription());
            statement.setString(++i, product.getProductCategory().toString());
            statement.setString(++i, product.getManufacturer());
            statement.setString(++i, product.getCountry());
            statement.setString(++i, product.getPhoto());
            statement.setString(++i, product.getName());

            statement.executeUpdate();
        }
    }

    @Override
    public Product getProductByName(Connection connection, String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_PRODUCT)) {
            statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {

                Product product = new Product();
                product.setName(rs.getString("name"));
                product.setPrice(rs.getLong("price"));
                product.setDescription(rs.getString("description"));
                product.setProductCategory(ProductCategory.valueOf(rs.getString("productCategory").toUpperCase()));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setCountry(rs.getString("country"));
                product.setPhoto(rs.getString("photo"));
                return product;
            }
        }
        return null;
    }

    @Override
    public List<Product> getAllProducts(Connection connection) throws SQLException {
        return getMultiplyProducts(connection, GET_ALL_PRODUCTS);
    }

    @Override
    public List<Product> getProductsByCredentionals(Connection connection, Map<String, Object> credentionals) throws SQLException {
        String query = createProductsByCredentionalsQuery(credentionals);
        return getMultiplyProducts(connection, query);

    }

    @Override
    public List<String> getCategories(Connection connection) throws SQLException {
        List<String> categories = new LinkedList<>();
        try (PreparedStatement statement = connection.prepareStatement(GET_CATEGORIES)) {
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                categories.add(rs.getString("productCategory"));
            }
        }
        return categories;
    }

    private List<Product> getMultiplyProducts(Connection connection, String query) throws SQLException{
        List<Product> products = new LinkedList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {

                Product product = new Product();
                product.setName(rs.getString("name"));
                product.setPrice(rs.getLong("price"));
                product.setDescription(rs.getString("description"));
                product.setProductCategory(ProductCategory.valueOf(rs.getString("productCategory").toUpperCase()));
                product.setManufacturer(rs.getString("manufacturer"));
                product.setCountry(rs.getString("country"));
                product.setPhoto(rs.getString("photo"));
                products.add(product);
            }
        }
        return products;
    }

    private String createProductsByCredentionalsQuery(Map<String, Object> credentionals){
        ProductFilterLink link = new CountryFilterLink(null);
        ProductFilterLink link2 = new DescriptionFilterLink(link);
        link = new ManufacturerFilterLink(link2);
        link2 = new NameFilterLink(link);
        link = new PhotoFilterLink(link2);
        link2 = new PriceFilterLink(link);
        link = new ProductCategoryFilterLink(link2);
        String finalQuery = GET_PRODUCTS_BY_CREDENTIONALS;
        return link.editQuery(finalQuery, credentionals);
    }

}
