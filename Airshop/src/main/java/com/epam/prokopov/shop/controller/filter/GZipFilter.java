package com.epam.prokopov.shop.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZipFilter implements Filter {

    private static final String CONTENT_TYPE = "text";
    private static final String CONTENT_ENCODING = "gzip";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (acceptsGZipEncoding(httpRequest)) {
            ResponseWrapper gzipResponse =
                    new ResponseWrapper(httpResponse);
            chain.doFilter(request, gzipResponse);
            if (httpResponse.getContentType() != null && httpResponse.getContentType().startsWith(CONTENT_TYPE)) {
                httpResponse.setHeader("Content-Encoding", CONTENT_ENCODING);
                GZIPOutputStream gzipOutputStream = new GZIPOutputStream(httpResponse.getOutputStream());
                gzipOutputStream.write(gzipResponse.getByteArray());
                gzipOutputStream.close();
            } else {
                httpResponse.getOutputStream().write(gzipResponse.getByteArray());
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private boolean acceptsGZipEncoding(HttpServletRequest httpRequest) {
        String acceptEncoding = httpRequest.getHeader("Accept-Encoding");
        return acceptEncoding != null && acceptEncoding.contains(CONTENT_ENCODING);
    }
}
