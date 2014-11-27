package com.epam.prokopov.shop.controller.filter.locale;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionLocaleManager extends LocaleManager{
    @Override
    public String getLocale(HttpServletRequest request) {
       return (String) request.getSession().getAttribute(LANGUAGE);
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, String locale) {
        request.getSession().setAttribute(LANGUAGE, locale);
    }
}
