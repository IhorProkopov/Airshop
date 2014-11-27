package com.epam.prokopov.shop.controller;


import com.epam.prokopov.shop.controller.filter.GenerationTimeFilter;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.FilterChain;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenerationTimeFilterTest {

    private static HttpServletRequest request;
    private static HttpServletResponse response;
    private static FilterChain chain;
    private static ByteArrayOutputStream outputStream;

    @BeforeClass
    public static void setUp() throws IOException {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        outputStream = new ByteArrayOutputStream();
        when(response.getWriter()).thenReturn(new PrintWriter(outputStream));
        when(response.getContentType()).thenReturn("text/html");
        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        });
        when(response.getCharacterEncoding()).thenReturn("utf-8");
    }

    @Test
    public void timeFilterTest() throws Exception {
        GenerationTimeFilter generationTimeFilter = new GenerationTimeFilter();

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                PrintWriter writer = ((HttpServletResponse) invocation.getArguments()[1]).getWriter();
                writer.write("<div id='timeDiv'></div>");
                writer.flush();
                return null;
            }
        }).when(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));
        generationTimeFilter.doFilter(request, response, chain);
        String result = outputStream.toString();
        assertTrue(result.contains("Page was generated for"));
    }
}
