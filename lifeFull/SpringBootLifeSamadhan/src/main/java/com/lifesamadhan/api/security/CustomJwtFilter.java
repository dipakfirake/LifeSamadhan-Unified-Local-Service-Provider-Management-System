package com.lifesamadhan.api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomJwtFilter extends OncePerRequestFilter {
    
    private static final Logger log = LoggerFactory.getLogger(CustomJwtFilter.class);

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsServiceImpl userDetailsService;
    
    public CustomJwtFilter(JwtUtils jwtUtils, CustomUserDetailsServiceImpl userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
        
        try {
            final String authorizationHeader = request.getHeader("Authorization");
            
            String username = null;
            String jwt = null;
            
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                try {
                    username = jwtUtils.extractUsername(jwt);
                } catch (Exception e) {
                    log.error("JWT token extraction failed: {}", e.getMessage());
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    
                    if (jwtUtils.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = 
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (Exception e) {
                    log.error("Authentication failed: {}", e.getMessage());
                }
            }
            
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("JWT Filter error: {}", e.getMessage());
            filterChain.doFilter(request, response);
        }
    }
}