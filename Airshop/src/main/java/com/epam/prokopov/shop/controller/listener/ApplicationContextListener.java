package com.epam.prokopov.shop.controller.listener;

import com.epam.prokopov.shop.controller.captcha.CaptchaSaver;
import com.epam.prokopov.shop.controller.captcha.CookieCapthaSaver;
import com.epam.prokopov.shop.controller.captcha.HiddenCaptchaSaver;
import com.epam.prokopov.shop.controller.captcha.SessionCaptchaSaver;
import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.model.UserRoles;
import com.epam.prokopov.shop.repository.*;
import com.epam.prokopov.shop.service.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@WebListener
public class ApplicationContextListener implements ServletContextListener {



    private final static String SESSION = "session";
    private final static String HIDDEN_FIELDS = "hidden_fields";
    private final static String COOKIE = "cookie";

    private final static String DB = "db";
    private final static String TEMP = "temp";
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        CaptchaSaver cs = null;
        Map<String, User> users = new HashMap<>();
        users.put("tw@gmail.com", new User("Tom", "Watson", "tw@gmail.com",
                "123qweQWE", false, "D:\\photo\\defaultAvatar.jpg", UserRoles.USER));
        users.put("th@gmail.com", new User("Tom", "Hanks", "th@gmail.com",
                "123qweQWE", true, "D:\\photo\\defaultAvatar.jpg", UserRoles.USER));
        users.put("tvorogofff@gmail.com",
                new User("Varenik", "Tvorogov", "tvorogofff@gmail.com",
                        "123qweQWE", true, "D:\\photo\\defaultAvatar.jpg", UserRoles.USER));
        users.put("ky@gmail.com", new User("Kate", "Yellow", "ky@gmail.com",
                "123qweQWE", false, "D:\\photo\\defaultAvatar.jpg", UserRoles.USER));
        ServletContext servletContext = sce.getServletContext();
        String conf = servletContext.getInitParameter("captchaType");
        int lifeTime = Integer.parseInt(servletContext
                .getInitParameter("lifeTime"));
        switch (conf) {
            case SESSION:
                cs = new SessionCaptchaSaver(lifeTime);
                break;
            case HIDDEN_FIELDS:
                cs = new HiddenCaptchaSaver(lifeTime);
                break;
            case COOKIE:
                cs = new CookieCapthaSaver(lifeTime);
                break;
        }

        UserService userService = null;
        DataSource dataSource;

        try {
            Context initContext = new InitialContext();
            dataSource = (DataSource) initContext.lookup("java:/comp/env/creation");
            createDB(dataSource);
            dataSource = (DataSource) initContext.lookup("java:/comp/env/jdbc/airshop");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }


        conf = servletContext.getInitParameter("dao");
        switch (conf) {
            case DB:
                userService = new UserServiceDBImpl(dataSource, new UserRepositoryDBImpl());
                break;
            case TEMP:
                userService = new UserServiceImpl(new UserRepositoryImpl(users));
                break;
        }

        servletContext.setAttribute("userLoginInfoService", new UserLoginInfoServiceDBImpl(dataSource, new UserLoginInfoRepositoryDBImpl()));
        servletContext.setAttribute("productService", new ProductServiceDBImpl(dataSource, new ProductRepositoryDBImpl()));
        servletContext.setAttribute("orderService", new OrderServiceDBImpl(dataSource, new OrderRepositoryDBImpl()));
        servletContext.setAttribute("captchaClass", cs);
        servletContext.setAttribute("validator", new RegistrationValidator());
        servletContext.setAttribute("userService", userService);
        if (conf.equals(HIDDEN_FIELDS)) {
            servletContext.setAttribute("isHidden", true);
        } else {
            servletContext.setAttribute("isHidden", false);
        }

    }

    private void createDB(DataSource dataSource){
        TransactionHelper transactionHelper = new TransactionHelper(dataSource);
        transactionHelper.execute(new Transaction() {
            @Override
            public <T> T execute(Connection connection) throws SQLException {
                Statement stmt = connection.createStatement();
//                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS airshop;");
//                stmt.executeUpdate("USE airshop;");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `user` (\n" +
                        " `key` INT NOT NULL AUTO_INCREMENT,\n" +
                        " `email` VARCHAR(45) NOT NULL,\n" +
                        " `password` VARCHAR(45) NOT NULL,\n" +
                        " `name` VARCHAR(45) NOT NULL,\n" +
                        " `surname` VARCHAR(45) NOT NULL,\n" +
                        " `photo` VARCHAR(450) NOT NULL,\n" +
                        " `role` VARCHAR(45) NOT NULL,\n" +
                        " `delivery` BOOLEAN NOT NULL,\n" +
                        " `last_visit_time` BIGINT NOT NULL,\n" +
                        " `tries` INT NOT NULL,\n" +
                        " `block_end_time` BIGINT NOT NULL,\n" +
                        " PRIMARY KEY (`key`),\n" +
                        " UNIQUE INDEX `email_UNIQUE` (`email` ASC))\n" +
                        "ENGINE = InnoDB;");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `product` (\n" +
                        " `key` INT NOT NULL AUTO_INCREMENT,\n" +
                        " `name` VARCHAR(45) NOT NULL,\n" +
                        " `price` BIGINT NOT NULL,\n" +
                        "`description` VARCHAR(245) NOT NULL,\n" +
                        "`productCategory` VARCHAR(45) NOT NULL,\n" +
                        "`manufacturer` VARCHAR(45) NOT NULL,\n" +
                        "`country` VARCHAR(45) NOT NULL,\n" +
                        " `photo` VARCHAR(450) NOT NULL,\n" +
                        " PRIMARY KEY (`key`),\n" +
                        " UNIQUE INDEX `name_UNIQUE` (`name` ASC))\n" +
                        "ENGINE = InnoDB;");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `airshop`.`order_table` (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `status` VARCHAR(45) NOT NULL,\n" +
                        "  `time` BIGINT NOT NULL,\n" +
                        "  `user_email` VARCHAR(45) NOT NULL,\n" +
                        " `props` VARCHAR(45) NOT NULL, `delivery` VARCHAR(45) NOT NULL, `payment` VARCHAR(45) NOT NULL, `comments` VARCHAR(150) NOT NULL, PRIMARY KEY (`id`),\n" +
                        "  INDEX `user_email_idx` (`user_email` ASC),\n" +
                        "  CONSTRAINT `user_email`\n" +
                        "    FOREIGN KEY (`user_email`)\n" +
                        "    REFERENCES `airshop`.`user` (`email`)\n" +
                        "    ON DELETE NO ACTION\n" +
                        "    ON UPDATE NO ACTION) \n" +
                        "ENGINE = InnoDB \n" +
                        "DEFAULT CHARACTER SET = utf8 \n" +
                        "COLLATE = utf8_general_ci;");
                stmt.executeUpdate("CREATE TABLE IF NOT EXISTS `airshop`.`order_info` (\n" +
                        "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                        "  `product_name` VARCHAR(45) NOT NULL,\n" +
                        "  `price` BIGINT NOT NULL,\n" +
                        "  `count` INT NOT NULL,\n" +
                        "  `order_id` INT NOT NULL,\n" +
                        "  PRIMARY KEY (`id`),\n" +
                        "  INDEX `name_idx` (`product_name` ASC),\n" +
                        "  INDEX `order_id_idx` (`order_id` ASC),\n" +
                        "  CONSTRAINT `product_name`\n" +
                        "    FOREIGN KEY (`product_name`)\n" +
                        "    REFERENCES `airshop`.`product` (`name`)\n" +
                        "    ON DELETE NO ACTION\n" +
                        "    ON UPDATE NO ACTION,\n" +
                        "  CONSTRAINT `order_id`\n" +
                        "    FOREIGN KEY (`order_id`)\n" +
                        "    REFERENCES `airshop`.`order_table` (`id`)\n" +
                        "    ON DELETE NO ACTION\n" +
                        "    ON UPDATE NO ACTION) \n" +
                        "ENGINE = InnoDB \n" +
                        "DEFAULT CHARACTER SET = utf8 \n" +
                        "COLLATE = utf8_general_ci;\n");
                return null;
            }
        });
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }

}
