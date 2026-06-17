package org.javierGomez18.clientes.cuentas.microservicio.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(scanBasePackages = "org.javierGomez18.clientes.cuentas.microservicio")
@EnableEurekaServer
public class ClientesCuentasMicroservicioApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClientesCuentasMicroservicioApplication.class, args);
    }
}
