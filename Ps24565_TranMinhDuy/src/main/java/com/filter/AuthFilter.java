package com.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model.User;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/Ps24565_TranMinhDuy/user/*","/Ps24565_TranMinhDuy/Account.jsp"})
public class AuthFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        User user = session != null ? (User) session.getAttribute("user") : null;
        System.out.println("AuthFilter running");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/views/login.jsp?error=Please%20login%20to%20use%20this%20function!");
        } else {
            chain.doFilter(req, resp);
            // Set timeout for session to 1 minute
            session.setMaxInactiveInterval(60*3);
        }
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub
    }
    
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }
    
}
