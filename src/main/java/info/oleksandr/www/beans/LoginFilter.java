package info.oleksandr.www.beans;

import info.oleksandr.www.entities.User;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		 

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// Get the loginBean from session attribute
		UserController loginBean = (UserController)((HttpServletRequest)request).getSession().getAttribute("userController");
        
        // For the first application request there is no loginBean in the session so user needs to log in
        // For other requests loginBean is present but we need to check if user has logged in successfully
        if (loginBean == null || !loginBean.getLogged() || ((HttpServletRequest)request).getSession(false)==null) {
            
        	String contextPath = ((HttpServletRequest)request).getContextPath();           
        
        	((HttpServletResponse)response).sendRedirect(contextPath + "/admin.xhtml");
            
        }         
        chain.doFilter(request, response);


	}

	@Override
	public void destroy() {
		 
	}

}
