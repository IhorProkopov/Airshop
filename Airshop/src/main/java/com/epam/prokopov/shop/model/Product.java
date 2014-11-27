package com.epam.prokopov.shop.model;


public class Product implements Comparable<Product>{

    private String name;
    private Long price;
    private String description;
    private ProductCategory productCategory;
    private String manufacturer;
    private String country;
    private String photo;

    public Product() {
    }

    public Product(String name, Long price, String description, ProductCategory productCategory, String manufacturer, String country, String photo) {

        this.name = name;
        this.price = price;
        this.description = description;
        this.productCategory = productCategory;
        this.manufacturer = manufacturer;
        this.country = country;
        this.photo = photo;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Product o) {
        return this.name.compareTo(o.getName());
    }
}
