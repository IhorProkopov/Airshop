package com.epam.prokopov.shop.controller;

import com.epam.prokopov.shop.controller.filter.GZipFilter;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.zip.GZIPInputStream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class GZipFilterTest {
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
        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public void write(int b) throws IOException {
                outputStream.write(b);
            }
        });
        when(response.getCharacterEncoding()).thenReturn("utf-8");
    }

    @Test
    public void noZippingTest() throws IOException, ServletException {
        when(request.getHeader("Accept-Encoding")).thenReturn(null);
        GZipFilter gZipFilter = new GZipFilter();
        gZipFilter.doFilter(request, response, chain);
        verify(chain).doFilter(request, response);
    }

    @Test
    public void zippingTest() throws IOException, ServletException {
        when(request.getHeader("Accept-Encoding")).thenReturn("gzip");
        GZipFilter gZipFilter = new GZipFilter();
        when(response.getContentType()).thenReturn("text/html");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                HttpServletResponse gzipResponse = (HttpServletResponse) invocation.getArguments()[1];
                gzipResponse.getWriter().write("blabla");
                return null;
            }
        }).when(chain).doFilter(any(ServletRequest.class), any(ServletResponse.class));
        gZipFilter.doFilter(request, response, chain);
        GZIPInputStream gzipInputStreamStream = new GZIPInputStream(
                new ByteArrayInputStream(outputStream.toByteArray()));
        byte[] buf = new byte[256];
        gzipInputStreamStream.read(buf);
        assertEquals("blabla", new String(buf).trim());
        verify(response).setHeader("Content-Encoding", "gzip");
    }

}
