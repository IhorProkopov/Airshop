package com.epam.prokopov.shop.controller.filter.locale;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieLocaleManager extends LocaleManager {

    private Integer lifeTime;

    public CookieLocaleManager(Integer lifeTime) {
        this.lifeTime = lifeTime;
    }

    @Override
    public String getLocale(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies==null){
            return null;
        }
        for (Cookie c : cookies) {
            if (c.getName().equals(LANGUAGE)) {
                return c.getValue();
            }
        }
        return null;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, String locale) {
        Cookie cookie = new Cookie(LANGUAGE, locale);
        cookie.setMaxAge(lifeTime);
        response.addCookie(cookie);
    }
}
