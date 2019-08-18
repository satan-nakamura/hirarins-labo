package labo.hirarins.legacy.app.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class EncodingFilter implements Filter {

    private static final Logger log = Logger.getLogger(EncodingFilter.class.getName());

    public void init(FilterConfig filterConfig) throws ServletException {
        // nothing to do
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
        
        HttpServletRequest sReq = (HttpServletRequest) request;
        sReq.setCharacterEncoding("UTF-8");
        log.info("setCharacterEncoding to utf-8");
        chain.doFilter(request, response);
	}

	public void destroy() {
		// nothing to do
	}

    
}