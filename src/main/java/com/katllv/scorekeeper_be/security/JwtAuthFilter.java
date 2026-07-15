package com.katllv.scorekeeper_be.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    // JwtService is injected to validate the JWT token from the request header
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response); // if no header, return and continue the filter chain without authentication
            return;
        }

        String token = authHeader.substring(7); // strip "Bearer " prefix

        try {
            DecodedJWT decoded = jwtService.validateAccessToken(token);
            String username = decoded.getSubject();

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, null,
                    Collections.emptyList());
            SecurityContextHolder.getContext().setAuthentication(authToken);
        } catch (JWTVerificationException e) {
            // invalid/expired token - just don't authenticate, let it fall through
        }

        filterChain.doFilter(request, response);
    }
}
