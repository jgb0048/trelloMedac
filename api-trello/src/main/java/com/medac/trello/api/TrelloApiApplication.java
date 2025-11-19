package com.medac.trello.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

// 👇 añade estos imports
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static com.medac.trello.api.resources.TrelloApi.BASE_API_PATH;

@SpringBootApplication
// 👇 ajusta rutas si las tuyas son distintas
@EnableJpaRepositories(basePackages = "com.medac.trello.api.model.repository")
@EntityScan(basePackages = "com.medac.trello.api.model.entity")
public class TrelloApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrelloApiApplication.class, args);
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setContextPath(BASE_API_PATH);
    }
}
