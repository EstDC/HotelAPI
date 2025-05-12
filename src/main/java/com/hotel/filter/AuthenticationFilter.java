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
        "/api/hoteles/search",
        "/api/hoteles/ciudades",
        "/api/hoteles/estrellas",
        "/api/hoteles/",
        "/api/hoteles/ciudad/",
        "/api/hoteles/estrellas/",
        "/api/usuarios/login",
        "/api/usuarios/registro",
        "/api/usuarios/recuperar-password",
        "/api/usuarios/cambiar-password",
        "/api/usuarios/verificar-email"
    );

    public AuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService, ObjectMapper objectMapper) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        String requestURI = request.getRequestURI();
        logger.info("Procesando petición: {} {} - Origin: {}", 
            request.getMethod(), 
            requestURI,
            request.getHeader("Origin"));
        
        // Log all headers for debugging
        logger.info("Headers de la petición:");
        Collections.list(request.getHeaderNames()).forEach(headerName -> 
            logger.info("{}: {}", headerName, request.getHeader(headerName)));

        // Permitir peticiones OPTIONS sin autenticación
        if (request.getMethod().equals("OPTIONS")) {
            logger.info("Petición OPTIONS detectada, permitiendo acceso");
            filterChain.doFilter(request, response);
            return;
        }

        // Verificar si es un endpoint público
        if (isPublicEndpoint(requestURI)) {
            logger.info("Endpoint público detectado: {}", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        logger.info("Endpoint no público, verificando token...");

        try {
            String token = extractToken(request);
            if (token == null) {
                logger.warn("Token no encontrado en la petición: {} {} - Headers: {}", 
                    request.getMethod(), 
                    requestURI,
                    Collections.list(request.getHeaderNames()).stream()
                        .collect(Collectors.toMap(name -> name, request::getHeader)));
                sendErrorResponse(response, "Token no encontrado", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            logger.debug("Token encontrado, validando...");
            if (!jwtTokenProvider.validateToken(token)) {
                logger.warn("Token inválido o expirado en la petición: {} {} - Token: {}", 
                    request.getMethod(), 
                    requestURI,
                    token);
                sendErrorResponse(response, "Token inválido o expirado", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String username = jwtTokenProvider.getEmailFromToken(token);
            logger.debug("Token válido para usuario: {}", username);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (userDetails == null) {
                logger.warn("Usuario no encontrado: {}", username);
                sendErrorResponse(response, "Usuario no encontrado", HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Autenticación exitosa para usuario: {}", username);

            filterChain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Error en autenticación para {} {}: {} - Stack trace: {}", 
                request.getMethod(), requestURI, e.getMessage(), e);
            sendErrorResponse(response, "Error de autenticación: " + e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private boolean isPublicEndpoint(String requestURI) {
        return publicEndpoints.stream().anyMatch(endpoint -> 
            requestURI.equals(endpoint) || 
            (endpoint.endsWith("/") && requestURI.startsWith(endpoint)) ||
            (!endpoint.endsWith("/") && requestURI.startsWith(endpoint + "/"))
        );
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.debug("Extrayendo token del header Authorization: {}", bearerToken);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            logger.debug("Token extraído: {}", token);
            return token;
        }
        logger.warn("No se pudo extraer el token del header Authorization");
        return null;
    }

    private void sendErrorResponse(HttpServletResponse response, String message, int status) throws IOException {
        response.setStatus(status);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
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