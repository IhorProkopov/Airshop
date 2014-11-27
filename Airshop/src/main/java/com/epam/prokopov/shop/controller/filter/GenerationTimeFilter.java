package com.epam.prokopov.shop.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;


public class GenerationTimeFilter implements Filter {

    private static final int MAX_GENERATION_TIME = 200;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        ResponseWrapper wrapper = new ResponseWrapper(httpServletResponse);
        long time = Calendar.getInstance().getTimeInMillis();
        chain.doFilter(httpServletRequest, wrapper);
        time = Calendar.getInstance().getTimeInMillis() - time;
        String contentType = wrapper.getContentType();
        if (contentType != null && contentType.startsWith("text")) {
            String page = wrapper.getString();
            String spanClass = time > MAX_GENERATION_TIME ? "class='error'" : "";
            page = page.replace("<div id='timeDiv'></div>", "<small " + spanClass + "> Page was generated for " + time + " ms</small>");
            httpServletResponse.getOutputStream().write(page.getBytes());
        } else {
            response.getOutputStream().write(wrapper.getByteArray());
        }
    }

    @Override
    public void destroy() {

    }
}
