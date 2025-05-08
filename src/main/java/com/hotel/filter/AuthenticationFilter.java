package com.hotel.filter;

import com.hotel.model.Usuario;
import com.hotel.model.Rol;
import com.hotel.service.UsuarioService;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Filtro de autenticación que valida las credenciales del usuario
 * y controla el acceso basado en roles.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@Component
public class AuthenticationFilter implements Filter {

    @Autowired
    private UsuarioService usuarioService;

    // Endpoints públicos que no requieren autenticación
    private static final List<String> PUBLIC_ENDPOINTS = Arrays.asList(
        "/api/usuarios/login",
        "/api/usuarios/registro",
        "/api/hoteles",
        "/api/hoteles/buscar",
        "/api/hoteles/buscar/ciudad",
        "/api/hoteles/buscar/pais",
        "/api/hoteles/buscar/estrellas",
        "/api/habitaciones/disponibles",
        "/api/habitaciones/hotel",
        // Swagger/OpenAPI endpoints
        "/api/swagger-ui",
        "/api/swagger-ui/",
        "/api/swagger-ui/index.html",
        "/api/api-docs",
        "/api/api-docs/",
        "/api/api-docs/swagger-config",
        "/v3/api-docs",
        "/v3/api-docs/",
        "/swagger-ui.html",
        "/swagger-ui/",
        "/swagger-ui/index.html"
    );

    // Mapeo de roles a endpoints permitidos
    private static final Map<Rol, Set<String>> ROLE_ENDPOINTS = Map.of(
        Rol.PUBLICO, Set.of(
            "/api/hoteles",
            "/api/hoteles/buscar",
            "/api/hoteles/buscar/ciudad",
            "/api/hoteles/buscar/pais",
            "/api/hoteles/buscar/estrellas",
            "/api/habitaciones/disponibles"
        ),
        Rol.CLIENTE, Set.of(
            "/api/usuarios/perfil",
            "/api/usuarios/datos-bancarios",
            "/api/reservas",
            "/api/reservas/buscar",
            "/api/pagos",
            "/api/servicios/solicitar",
            "/api/extras/agregar"
        ),
        Rol.ADMIN, Set.of(
            "/api/usuarios",
            "/api/hoteles",
            "/api/habitaciones",
            "/api/reservas",
            "/api/pagos",
            "/api/servicios",
            "/api/extras"
        )
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Verificar si el endpoint es público
        String requestPath = httpRequest.getRequestURI();
        if (isPublicEndpoint(requestPath)) {
            chain.doFilter(request, response);
            return;
        }

        // Obtener credenciales del header
        String email = httpRequest.getHeader("X-User-Email");
        String password = httpRequest.getHeader("X-User-Password");

        // Validar credenciales
        if (email == null || password == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Credenciales no proporcionadas");
            return;
        }

        // Validar usuario contra la base de datos
        Usuario usuario = usuarioService.validarCredenciales(email, password);
        if (usuario == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Credenciales inválidas");
            return;
        }

        // Validar permisos según el rol
        if (!tienePermiso(usuario.getRol(), requestPath, httpRequest.getMethod())) {
            httpResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpResponse.getWriter().write("No tiene permiso para acceder a este recurso");
            return;
        }

        // Agregar el usuario al request para uso posterior
        request.setAttribute("usuario", usuario);
        chain.doFilter(request, response);
    }

    private boolean isPublicEndpoint(String path) {
        return PUBLIC_ENDPOINTS.stream()
                .anyMatch(endpoint -> path.startsWith(endpoint));
    }

    private boolean tienePermiso(Rol rol, String path, String method) {
        Set<String> endpointsPermitidos = ROLE_ENDPOINTS.get(rol);
        if (endpointsPermitidos == null) {
            return false;
        }

        // Verificar si el path comienza con alguno de los endpoints permitidos
        return endpointsPermitidos.stream()
                .anyMatch(endpoint -> path.startsWith(endpoint));
    }
} 