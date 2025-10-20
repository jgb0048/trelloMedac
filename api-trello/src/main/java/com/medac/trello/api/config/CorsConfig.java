//package com.medac.trello.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Aplica la configuración a todas las rutas de la API
                registry.addMapping("/**")
                        // Permite solicitudes únicamente desde tu frontend
                        .allowedOrigins("http://localhost:3000")
                        // Permite los métodos que usarás
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        // Permite cualquier encabezado en la solicitud
                        .allowedHeaders("*")
                        // Permite el envío de cookies o credenciales de autenticación
                        .allowCredentials(true);
            }
        };
    }
}



/*package com.medac.trello.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración global para habilitar Cross-Origin Resource Sharing (CORS).
 * Resuelve el error 500 causado por la combinación de allowCredentials(true) y allowedOrigins("*").
 * Spring exige especificar el origen cuando se permiten credenciales.
 */
/*@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                // SOLUCIÓN: Usar el origen explícito. No se puede usar "*" con allowCredentials(true).
                .allowedOrigins("http://localhost:3000")

                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                // Se mantiene para la autenticación
                .allowCredentials(true);
    }
}
*/

