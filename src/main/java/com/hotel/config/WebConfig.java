package com.hotel.config;

import com.hotel.filter.AuthenticationFilter;
import com.hotel.filter.CorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter as SpringCorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración web de la aplicación.
 * Registra los filtros y configuraciones necesarias.
 * 
 * @author Hotel API Team
 * @version 1.0
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationFilter authenticationFilter;

    @Autowired
    private CorsFilter corsFilter;

    /**
     * Registra el filtro de autenticación.
     *
     * @return Bean de registro del filtro
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistration() {
        FilterRegistrationBean<CorsFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(corsFilter);
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registration.setEnabled(true);
        registration.setName("corsFilter");
        registration.setAsyncSupported(true);
        return registration;
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilterRegistration() {
        FilterRegistrationBean<AuthenticationFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(authenticationFilter);
        registration.addUrlPatterns("/api/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
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
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/**", "/api/hoteles/**", "/api/habitaciones/**");
    }
} 