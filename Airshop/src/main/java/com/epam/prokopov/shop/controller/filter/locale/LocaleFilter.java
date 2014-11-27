package com.epam.prokopov.shop.controller.filter.locale;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class LocaleFilter implements Filter {

    protected static final String LANGUAGES = "languages";

    private LocaleManager localeManager;
    private List<String> locales;
    private String defaultLocale;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (filterConfig.getInitParameter("manager").equals("session")) {
            localeManager = new SessionLocaleManager();
        } else {
            localeManager = new CookieLocaleManager(new Integer(filterConfig.getInitParameter("lifeTime")));
        }
        locales = Arrays.asList(filterConfig.getInitParameter("locales").split(","));
        defaultLocale = filterConfig.getInitParameter("default_locale");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String locale = localeManager.getLocaleFromRequestParameter(request);
        if (!locales.contains(locale)) {
            locale = null;
        }
        if (locale == null) {
            locale = localeManager.getLocale(request);
            if (locale == null) {
                Enumeration<Locale> requestLocales = request.getLocales();
                while (requestLocales.hasMoreElements()) {
                    String tempLocale = requestLocales.nextElement().toLanguageTag();
                    if (locales.contains(tempLocale)) {
                        locale = tempLocale;
                        break;
                    }
                }
                if (locale == null) {
                    locale = defaultLocale;
                }
            }
        }
        localeManager.setLocale(request, response, locale);
        HttpServletRequest wrapper = getRequestWrapper(request, locale);
        wrapper.setAttribute(LANGUAGES, locales);
        chain.doFilter(wrapper, response);
    }

    @Override
    public void destroy() {
    }

    private HttpServletRequest getRequestWrapper(HttpServletRequest request, String locale) {
        final Locale loc = new Locale(locale);
        return new HttpServletRequestWrapper(request) {
            @Override
            public Locale getLocale() {
                return loc;
            }

            @Override
            public Enumeration<Locale> getLocales() {
                return Collections.enumeration(Arrays.asList(loc));
            }

        };
    }

}
