package com.dev.spring.security.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.dev.spring.security.jwt.util.JWTUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JWTUtil util;
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// Reading Token from Authorization Header
		String token= request.getHeader("Authorization");
		if(token !=null) {
			String username= util.getSubject(token);
			//if username is not null & Context Authentication must be null
			if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
				UserDetails user= userDetailsService.loadUserByUsername(username);
				boolean isValid=util.isValidToken(token, user.getUsername());
				if(isValid) {
					UsernamePasswordAuthenticationToken authToken= 
							new UsernamePasswordAuthenticationToken(username, user.getPassword(), user.getAuthorities());
					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authToken);
							
				}
			}
		}
		filterChain.doFilter(request, response);
	}

}