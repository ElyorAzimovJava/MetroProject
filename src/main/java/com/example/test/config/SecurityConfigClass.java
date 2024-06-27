package com.example.test.config;

import com.example.test.dto.exception.ExceptionResponse;
import com.example.test.jwt.JwtFilter;
import com.example.test.module.User;
import com.example.test.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletOutputStream;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;

import java.lang.reflect.Array;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigClass {
    private final ObjectMapper objectMapper;
    private final JwtFilter jwtFilter;
    private final String[] WHILE_LIST = {
            "/",
            "/api/v1/auth/login",
            "/api/v1/auth/register",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "v3/api-docs/**",
            "/department/allDepartments",
            "/position/getPositionByDepartmentId"
    };
    @Bean
    public SecurityFilterChain securityConfig(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        auth ->
                                auth.requestMatchers(WHILE_LIST).permitAll()
                                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> {
                            exceptionHandling.accessDeniedHandler(accessDeniedHandler())
                                    .authenticationEntryPoint(unauthorized());
                        }
                )

                .addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .build();

    }
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){

        return (request, response, accessDeniedException) -> {
            String requestURI = request.getRequestURI();
            String message = accessDeniedException.getMessage();
            Integer statusCode = 403;
            ExceptionResponse error = new ExceptionResponse(message, statusCode, requestURI, LocalDateTime.now());
            response.sendError(403);
            ServletOutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream,error);
        };
    }
    @Bean
    public AuthenticationEntryPoint unauthorized(){
        return (request, response, authException) -> {
            String requestURI = request.getRequestURI();
            String message = authException.getMessage();
            Integer statusCode = 401;
            ExceptionResponse error = new ExceptionResponse(message, statusCode, requestURI, LocalDateTime.now());
            response.sendError(401);
            ServletOutputStream outputStream = response.getOutputStream();
            objectMapper.writeValue(outputStream,error);
        };
    }



}
