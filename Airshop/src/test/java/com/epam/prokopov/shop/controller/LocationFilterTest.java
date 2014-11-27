package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.controller.filter.locale.LocaleFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LocationFilterTest {

    private Filter filter;
    private ArgumentCaptor<Cookie> argument;
    private HttpServletRequest request;
    private static HttpServletResponse response;
    private static FilterConfig filterConfig;
    private static FilterChain chain;
    private HttpSession session;
    private static final String DEFAULT_LOCALE = "en";

    @BeforeClass
    public static void setUp() {
        filterConfig = mock(FilterConfig.class);
        chain = mock(FilterChain.class);
        when(filterConfig.getInitParameter("locales")).thenReturn("en,ru");
        when(filterConfig.getInitParameter("default_locale")).thenReturn(DEFAULT_LOCALE);
        when(filterConfig.getInitParameter("lifeTime")).thenReturn("6000000");
    }

    @Before
    public void initialize() {
        session = mock(HttpSession.class);
        filter = new LocaleFilter();
        response = mock(HttpServletResponse.class);
        request = mock(HttpServletRequest.class);
        argument = ArgumentCaptor.forClass(Cookie.class);
        when(request.getSession()).thenReturn(session);
        when(request.getCookies()).thenReturn(new Cookie[]{});
        when(request.getLocales()).thenReturn(Collections.enumeration(Arrays.asList(new Locale("en"))));
    }

    @Test
    public void cookieLocalizationWithoutRequestedLanguage() throws ServletException, IOException {
        when(filterConfig.getInitParameter("manager")).thenReturn("cookie");
        filter.init(filterConfig);
        filter.doFilter(request, response, chain);
        verify(response).addCookie(argument.capture());
        assertEquals("language", argument.getValue().getName());
        assertEquals("en", argument.getValue().getValue());
    }

    @Test
    public void cookieLocalizationWithContainsLanguage() throws ServletException, IOException {
        when(filterConfig.getInitParameter("manager")).thenReturn("cookie");
        when(request.getParameter("lang")).thenReturn("ru");
        filter.init(filterConfig);
        filter.doFilter(request, response, chain);
        verify(response).addCookie(argument.capture());
        assertEquals("language", argument.getValue().getName());
        assertEquals("ru", argument.getValue().getValue());
    }

    @Test
    public void cookieLocalizationWithUncontainsLanguage() throws ServletException, IOException {
        when(filterConfig.getInitParameter("manager")).thenReturn("cookie");
        when(request.getParameter("lang")).thenReturn("kz");
        filter.init(filterConfig);
        filter.doFilter(request, response, chain);
        verify(response).addCookie(argument.capture());
        assertEquals("language", argument.getValue().getName());
        assertEquals("en", argument.getValue().getValue());
    }

    @Test
    public void sessionLocalizationWithoutRequestedLanguage() throws ServletException, IOException {
        when(filterConfig.getInitParameter("manager")).thenReturn("session");
        filter.init(filterConfig);
        filter.doFilter(request, response, chain);
        verify(session).setAttribute("language", "en");
    }

    @Test
    public void sessionLocalizationWithContainsLanguage() throws ServletException, IOException {
        when(filterConfig.getInitParameter("manager")).thenReturn("session");
        when(request.getParameter("lang")).thenReturn("ru");
        filter.init(filterConfig);
        filter.doFilter(request, response, chain);
        verify(session).setAttribute("language", "ru");
    }

    @Test
    public void sessionLocalizationWithUncontainsLanguage() throws ServletException, IOException {
        when(filterConfig.getInitParameter("manager")).thenReturn("session");
        when(request.getParameter("lang")).thenReturn("kz");
        filter.init(filterConfig);
        filter.doFilter(request, response, chain);
        verify(session).setAttribute("language", "en");
    }

}
