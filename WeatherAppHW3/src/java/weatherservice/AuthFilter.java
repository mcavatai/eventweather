/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weatherservice;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mike
 */
public class AuthFilter implements javax.servlet.Filter {

    protected ServletContext context;
    
    public void init(FilterConfig filterConfig) throws ServletException {
        context = filterConfig.getServletContext();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpSession session = req.getSession(true);
        
        if (!isAuth(session)) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        
        chain.doFilter(request, response);
    }
    
    protected boolean isAuth(HttpSession session) {
        if (null == session.getAttribute("found")||session.getAttribute("found").equals(false)) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void destroy() {
        context = null;
    }
}
