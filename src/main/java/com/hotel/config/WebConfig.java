package com.hotel.config;

import com.hotel.filter.AuthenticationFilter;
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
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.List;

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
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
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
        // Eliminado: registry.addInterceptor(new AuthenticationInterceptor())
        //         .addPathPatterns("/api/**")
        //         .excludePathPatterns("/api/auth/**", "/api/hoteles/**", "/api/habitaciones/**");
    }

    //WebMvcConfig
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configurar los recursos estáticos para que no interfieran con las rutas de la API
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    //Configuración global de los media types soportados
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter jacksonConverter) {
                jacksonConverter.setSupportedMediaTypes(
                    List.of(
                        org.springframework.http.MediaType.APPLICATION_JSON,
                        org.springframework.http.MediaType.valueOf("application/json;charset=UTF-8")
                    )
                );
            }
        }
    }
} 