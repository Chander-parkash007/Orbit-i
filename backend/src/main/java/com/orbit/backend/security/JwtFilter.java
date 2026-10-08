package com.orbit.backend.security;

import com.orbit.backend.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtill jwtUtill;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //get authHeader
        String authHeader = request.getHeader("Authorization");
        //check if it has token or pass it
    if (authHeader == null || !authHeader.startsWith("Bearer ")){
        filterChain.doFilter(request,response);
        return;
    }
    //get token
    String token = authHeader.substring(7);
//if token not valid pass it
    if (!jwtUtill.isTokenValid(token)){
        filterChain.doFilter(request,response);
        return;
    }
    //if token valid then extract email
    String email = jwtUtill.extractEmail(token);
    //if user not already authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request,response);


    }
}
