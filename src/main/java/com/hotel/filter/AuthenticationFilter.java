package com.hotel.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import com.hotel.service.UsuarioService;
import com.hotel.security.JwtTokenProvider;
import com.hotel.model.Usuario;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Filtro de autenticación que valida las credenciales del usuario
 * y controla el acceso basado en roles.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@Component
public class AuthenticationFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // Endpoints públicos que no requieren autenticación
    private static final List<String> PUBLIC_ENDPOINTS = Arrays.asList(
        "/api/usuarios/login",
        "/api/usuarios/registro",
        "/api/usuarios/recuperar-password",
        "/api/usuarios/cambiar-password",
        "/api/hoteles",
        "/api/hoteles/**",
        "/api/habitaciones/disponibles",
        "/api/habitaciones/hotel/**",
        "/api/servicios/**",
        "/api/swagger-ui/**",
        "/api/api-docs/**",
        "/v3/api-docs/**",
        "/swagger-ui/**"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String contextPath = httpRequest.getContextPath();
        String requestURI = httpRequest.getRequestURI();
        String requestPath = requestURI.substring(contextPath.length());
        
        logger.debug("Procesando petición: {} {}", httpRequest.getMethod(), requestPath);

        // Verificar si es un endpoint público
        if (isPublicEndpoint(requestPath)) {
            logger.debug("Endpoint público detectado: {}", requestPath);
            chain.doFilter(request, response);
            return;
        }

        // Obtener token del header Authorization
        String authHeader = httpRequest.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("No se encontró token en el header");
            sendUnauthorizedResponse(httpResponse, "Se requiere token de autenticación");
            return;
        }

        // Extraer y validar token
        String token = authHeader.substring(7);
        if (!tokenProvider.validateToken(token)) {
            logger.warn("Token inválido o expirado");
            sendUnauthorizedResponse(httpResponse, "Token inválido o expirado");
            return;
        }

        // Obtener email del token y verificar que el usuario existe y está activo
        String email = tokenProvider.getEmailFromToken(token);
        Usuario usuario = usuarioService.obtenerUsuarioPorEmail(email);
        
        if (usuario != null && usuario.isActivo()) {
            logger.debug("Usuario autenticado: {}", email);
            chain.doFilter(request, response);
        } else {
            logger.warn("Usuario no encontrado o inactivo: {}", email);
            sendUnauthorizedResponse(httpResponse, "Usuario no encontrado o inactivo");
        }
    }

    private void sendUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(message);
    }

    private boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.stream()
            .anyMatch(pattern -> pathMatcher.match(pattern, path));
    }
} 