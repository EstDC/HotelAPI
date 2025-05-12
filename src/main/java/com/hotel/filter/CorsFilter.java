package com.hotel.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CorsFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        logger.debug("Procesando solicitud CORS: {} {} - Origin: {}", 
            request.getMethod(), 
            request.getRequestURI(),
            request.getHeader("Origin"));

        // Configurar headers CORS
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:4321");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept, X-Requested-With, Origin, Access-Control-Request-Method, Access-Control-Request-Headers");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Expose-Headers", "Authorization, Content-Type");

        // Manejar preflight requests
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            logger.debug("Petición OPTIONS detectada, respondiendo con 200 OK");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            logger.error("Error en CORS filter: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Error interno del servidor\"}");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Inicializando filtro CORS");
    }

    @Override
    public void destroy() {
        logger.info("Destruyendo filtro CORS");
    }
} 