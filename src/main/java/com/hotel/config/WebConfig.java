package com.hotel.config;

import com.hotel.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración web de la aplicación.
 * Registra los filtros y configuraciones necesarias.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    /**
     * Registra el filtro de autenticación.
     *
     * @return Bean de registro del filtro
     */
    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistration() {
        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(authenticationFilter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(1);
        registration.setEnabled(true);
        registration.setName("authenticationFilter");
        registration.setAsyncSupported(true);
        registration.setDispatcherTypes(
            jakarta.servlet.DispatcherType.REQUEST,
            jakarta.servlet.DispatcherType.ASYNC,
            jakarta.servlet.DispatcherType.ERROR
        );
        return registration;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:4321") // Origen específico del frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .maxAge(3600);
    }
} 