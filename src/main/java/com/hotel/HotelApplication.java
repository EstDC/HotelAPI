package com.hotel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(
        title = "Hotel API",
        version = "1.0",
        description = "API para sistema de reservas de hotel",
        contact = @Contact(
            name = "Hotel API Support",
            email = "support@hotelapi.com",
            url = "https://hotelapi.com/support"
        ),
        license = @License(
            name = "Apache 2.0",
            url = "https://www.apache.org/licenses/LICENSE-2.0"
        )
    ),
    servers = {
        @Server(
            url = "http://localhost:8081",
            description = "Servidor de Desarrollo"
        ),
        @Server(
            url = "https://api.hotelapi.com",
            description = "Servidor de Producción"
        )
    },
    tags = {
        @Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios"),
        @Tag(name = "Hoteles", description = "Operaciones relacionadas con hoteles"),
        @Tag(name = "Habitaciones", description = "Operaciones relacionadas con habitaciones"),
        @Tag(name = "Reservas", description = "Operaciones relacionadas con reservas"),
        @Tag(name = "Pagos", description = "Operaciones relacionadas con pagos"),
        @Tag(name = "Servicios", description = "Operaciones relacionadas con servicios"),
        @Tag(name = "Extras", description = "Operaciones relacionadas con extras")
    }
)
public class HotelApplication {
    public static void main(String[] args) {
        SpringApplication.run(HotelApplication.class, args);
    }
} 