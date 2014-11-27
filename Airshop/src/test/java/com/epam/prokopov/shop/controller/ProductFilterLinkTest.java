package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.repository.chain.filter.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ProductFilterLinkTest {

    private static final String GET_PRODUCTS_BY_CREDENTIONALS = "SELECT * FROM product WHERE ;";
    private static ProductFilterLink chain;
    private Map<String, Object> credentionals = new HashMap<>();

    @BeforeClass
    public static void setUp(){
        chain = new CountryFilterLink(null);
        ProductFilterLink link2 = new DescriptionFilterLink(chain);
        chain = new ManufacturerFilterLink(link2);
        link2 = new NameFilterLink(chain);
        chain = new PhotoFilterLink(link2);
        link2 = new PriceFilterLink(chain);
        chain = new ProductCategoryFilterLink(link2);
    }

    @Before
    public void initilaze(){
        credentionals = new HashMap<>();
        credentionals.put("country", "Russia");
        credentionals.put("description", "this is description");
        credentionals.put("manufacturer", "Russia");
        credentionals.put("name", "AH-225");
        credentionals.put("photo", false);
        List<String> categories = new LinkedList<>();
        categories.add("bomber");
        categories.add("airliner");
        categories.add("frontline");
        credentionals.put("categories", categories);
    }

    @Test
    public void fullChainTest(){
        String query = GET_PRODUCTS_BY_CREDENTIONALS;
        query = chain.editQuery(query, credentionals);
        Assert.assertTrue(query.equals("SELECT * FROM product WHERE ( productCategory = 'bomber' OR productCategory = 'airliner' OR productCategory = 'frontline')  AND photo LIKE '%defaultAvatar.jpg' AND name LIKE '%AH-225%' AND manufacturer LIKE '%Russia%' AND description LIKE '%this is description%' AND country LIKE '%Russia%';"));
    }

    @Test
    public void emptyChainTest(){
        String query = GET_PRODUCTS_BY_CREDENTIONALS;
        query = chain.editQuery(query, new HashMap<String, Object>());
        Assert.assertTrue(query.equals("SELECT * FROM product;"));
    }

    @Test
    public void notFullChainTest(){
        String query = GET_PRODUCTS_BY_CREDENTIONALS;
        credentionals.remove("name");
        query = chain.editQuery(query, credentionals);
        Assert.assertTrue(query.equals("SELECT * FROM product WHERE ( productCategory = 'bomber' OR productCategory = 'airliner' OR productCategory = 'frontline')  AND photo LIKE '%defaultAvatar.jpg' AND manufacturer LIKE '%Russia%' AND description LIKE '%this is description%' AND country LIKE '%Russia%';"));
    }

}
