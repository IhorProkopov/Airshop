package com.epam.prokopov.shop.controller.filter.locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class LocaleManager {

    protected static final String LANGUAGE = "language";
    protected static final String LANG = "lang";

    public abstract String getLocale(HttpServletRequest request);

    public abstract void setLocale(HttpServletRequest request, HttpServletResponse response, String locale);

    public String getLocaleFromRequestParameter(HttpServletRequest request){
        return request.getParameter(LANG);
    }

}
