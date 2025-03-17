package by.innowise.imageservice.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Image Service API",
                version = "1.0.0",
                description = "Документация API для микросервиса изображений"
        ),
        servers = @Server(url = "/")
)
public class OpenApiConfig {
}


