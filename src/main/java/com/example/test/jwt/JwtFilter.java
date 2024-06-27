package com.example.test.jwt;


import com.example.test.dto.exception.ExceptionResponse;
import com.example.test.util.Variables;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String token;
        final String authHeader = request.getHeader(Variables.AUTHENTICATION_HEADER);
         String email="";
        if(authHeader == null || !authHeader.startsWith(Variables.TOKEN_TYPE)){
            filterChain.doFilter(request,response);
            return;
        }
            token = authHeader.substring(7);

        try {
            email = jwtService.getUsernameInToken(token);
        }catch (ExpiredJwtException e){
            handleExpiredJwtException(request,response,e);
            return;
        }
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                if (jwtService.tokenIsExpired(token)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
        }
         filterChain.doFilter(request,response);
    }
    private void handleExpiredJwtException(HttpServletRequest request, HttpServletResponse response, ExpiredJwtException e) throws IOException {
        String requestURI = request.getRequestURI();
        String message = "JWT expired: " + e.getMessage();
        Integer statusCode = HttpServletResponse.SC_UNAUTHORIZED;
        ExceptionResponse error = new ExceptionResponse(message, statusCode, requestURI, LocalDateTime.now());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ServletOutputStream outputStream = response.getOutputStream();
        objectMapper.writeValue(outputStream, error);
    }
}
