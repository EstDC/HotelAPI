package com.hotel.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.security.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filtro de autenticación que valida las credenciales del usuario
 * y controla el acceso basado en roles.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final List<String> publicEndpoints = Arrays.asList(
        "/api/auth/login",
        "/api/auth/register",
        "/api/hoteles",
        "/api/hoteles/",
        "/api/hoteles/**",
        "/api/habitaciones",
        "/api/habitaciones/",
        "/api/habitaciones/**",
        "/api/usuarios/login",
        "/api/usuarios/registro",
        "/api/usuarios/recuperar-password",
        "/api/usuarios/cambiar-password",
        "/api/usuarios/verificar-email",
        "/api/destinos",
        "/api/destinos/",
        "/api/destinos/populares",
        "/api/destinos/buscar",
        // Swagger UI
        "/swagger-ui/**",
        "/swagger-ui.html",
        "/swagger-ui/index.html",
        "/swagger-ui/swagger-initializer.js",
        "/swagger-ui/swagger-ui.css",
        "/swagger-ui/swagger-ui-bundle.js",
        "/swagger-ui/swagger-ui-standalone-preset.js",
        "/swagger-ui/favicon-32x32.png",
        "/swagger-ui/favicon-16x16.png",
        // OpenAPI
        "/v3/api-docs/**",
        "/v3/api-docs",
        "/v3/api-docs.yaml",
        "/v3/api-docs.yml",
        "/v3/api-docs.json",
        // Swagger Resources
        "/swagger-resources/**",
        "/swagger-resources",
        "/swagger-resources/configuration/ui",
        "/swagger-resources/configuration/security",
        // Webjars
        "/webjars/**",
        "/webjars",
        // Favicon
        "/favicon.ico",
        // Swagger Config
        "/api-docs/**",
        "/api-docs",
        "/api-docs/swagger-config",
        "/api-docs/swagger-config.json",
        "/api-docs/swagger-config.yaml",
        "/api-docs/swagger-config.yml"
    );
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public AuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, ObjectMapper objectMapper) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String requestPath = request.getRequestURI();
        
        // Permitir peticiones OPTIONS sin autenticación
        if (request.getMethod().equals("OPTIONS")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Verificar si es un endpoint público de manera más eficiente
        boolean isPublicEndpoint = publicEndpoints.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestPath));

        if (isPublicEndpoint) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = extractToken(request);
            if (token == null) {
                logger.warn("Token no encontrado en la petición: {} {}", request.getMethod(), requestPath);
                sendErrorResponse(response, "Token no encontrado", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            if (!jwtTokenProvider.validateToken(token)) {
                logger.warn("Token inválido o expirado en la petición: {} {}", request.getMethod(), requestPath);
                sendErrorResponse(response, "Token inválido o expirado", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String username = jwtTokenProvider.getEmailFromToken(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (userDetails == null) {
                logger.warn("Usuario no encontrado: {}", username);
                sendErrorResponse(response, "Usuario no encontrado", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
            );
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Error en el filtro de autenticación: {}", e.getMessage(), e);
            sendErrorResponse(response, "Error de autenticación", HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        
        if (bearerToken == null) {
            bearerToken = request.getHeader("authorization");
        }
        if (bearerToken == null) {
            bearerToken = request.getHeader("x-auth-token");
        }
        
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new ErrorResponse(message)));
    }

    private static class ErrorResponse {
        private final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }
    }
} 