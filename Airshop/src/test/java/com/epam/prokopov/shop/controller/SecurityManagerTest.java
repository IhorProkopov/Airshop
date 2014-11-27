package com.epam.prokopov.shop.controller;


import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.model.UserRoles;
import com.epam.prokopov.shop.service.SecurityManager;
import org.junit.Assert;
import org.junit.Test;

public class SecurityManagerTest {

    private SecurityManager securityManager = new SecurityManager("src/main/webapp/WEB-INF/security.xml");

    @Test
    public void hasRestrictionsTest(){
        boolean res = securityManager.hasRestrictions("/Airshop/adminpage");
        Assert.assertTrue(res);
    }

    @Test
    public void hasNoRestrictionsTest(){
        boolean res = securityManager.hasRestrictions("/Airshop/goods");
        Assert.assertFalse(res);
    }

    @Test
    public void checkHasAcess(){
        User user  = new User();
        user.setRole(UserRoles.ADMIN);
        Assert.assertTrue(securityManager.checkAcess("/Airshop/adminpage", user));
    }

    @Test
    public void checkHasNoAcess(){
        User user  = new User();
        user.setRole(UserRoles.USER);
        Assert.assertFalse(securityManager.checkAcess("/Airshop/adminpage", user));
    }

}
