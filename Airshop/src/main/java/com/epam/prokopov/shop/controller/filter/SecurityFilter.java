package com.epam.prokopov.shop.controller.filter;


import com.epam.prokopov.shop.model.User;
import com.epam.prokopov.shop.service.SecurityManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SecurityFilter implements Filter {

    SecurityManager securityManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        securityManager = new SecurityManager(filterConfig.getServletContext().getRealPath(filterConfig.getInitParameter("security.xml")));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String url = httpRequest.getRequestURI();
        if (securityManager.hasRestrictions(url)) {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                session.setAttribute("targetPage", url);
                httpRequest.getRequestDispatcher("/login").forward(request, response);
                return;
            } else {
                if (!securityManager.checkAcess(url, user)) {
                    httpResponse.sendError(403, "Forbidden");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
