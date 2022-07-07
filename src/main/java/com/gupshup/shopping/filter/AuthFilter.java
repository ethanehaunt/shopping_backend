package com.gupshup.shopping.filter;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter implements Filter {
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterchain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		ArrayList<String> skipsessioncheckrequests = new ArrayList<String>();
		skipsessioncheckrequests.add("/login");
		
		
		ArrayList<String> allowedfileextensions = new ArrayList<String>();
		allowedfileextensions.add(".js");
		allowedfileextensions.add(".css");
		allowedfileextensions.add(".ico");
		allowedfileextensions.add(".woff");
		allowedfileextensions.add(".woff2");
		allowedfileextensions.add(".png");
		allowedfileextensions.add(".jpg");
		allowedfileextensions.add(".ttf");
				
		try {
		
    		HttpServletRequest validatedrequest = FilterUtil.applyAuthFilter(httpRequest);
			filterchain.doFilter(validatedrequest, response);
		} 
		catch(Throwable e) {
			
			HttpServletResponse httpresponse = (HttpServletResponse) response;
			httpresponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		
	}

}
