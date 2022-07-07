package com.gupshup.shopping.filter;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.servlet.http.HttpServletRequest;

public class FilterUtil {
	
	public static HttpServletRequest applyAuthFilter(HttpServletRequest request) throws Throwable {
		
		String authheader = request.getHeader("Authentication");

		if(authheader != null && !authheader.isEmpty()) {
			
			request.setAttribute("user_token", authheader);
			
			return request;
			
		}
		else {
			return request;
		}
	}

}
