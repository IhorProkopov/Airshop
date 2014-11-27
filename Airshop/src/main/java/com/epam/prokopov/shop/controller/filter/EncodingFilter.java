package com.epam.prokopov.shop.controller.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final String ENCODING = "UTF-8";
    private static final String CONTENT_TYPE = "text";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (httpResponse.getContentType() != null && httpResponse.getContentType().startsWith(CONTENT_TYPE)) {
            if (httpRequest.getCharacterEncoding() == null) {
                httpRequest.setCharacterEncoding(ENCODING);

            }
            response.setCharacterEncoding(ENCODING);
            httpResponse.setHeader("Cache-Control",
                    "no-cache, no-store");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
