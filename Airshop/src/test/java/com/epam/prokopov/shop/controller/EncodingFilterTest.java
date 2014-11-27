package com.epam.prokopov.shop.controller;


import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.prokopov.shop.controller.filter.EncodingFilter;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class EncodingFilterTest {

    private static final String ENCODING = "UTF-8";
    private EncodingFilter filter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;

    @Before
    public void setUp() {
        filter = new EncodingFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        when(response.getContentType()).thenReturn("text");
    }

    @Test
    public void encodingFilterTest() throws Exception {
        filter.doFilter(request, response, chain);
        verify(request).setCharacterEncoding(ENCODING);
        verify(response).setCharacterEncoding(ENCODING);
        verify(response).setHeader(eq("Cache-Control"),
                eq("no-cache, no-store"));
        verify(chain).doFilter(request, response);
    }

}