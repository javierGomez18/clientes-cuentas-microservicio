package org.javierGomez18.clientes.cuentas.microservicio.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
    scanBasePackages = {
      "org.javierGomez18.clientes.cuentas.microservicio",
      "com.javier.infrastructure.auditclient"
    })
@EnableFeignClients(basePackages = "com.javier.infrastructure.auditclient")
public class ClientesCuentasMicroservicioApplication {
  public static void main(String[] args) {
    SpringApplication.run(ClientesCuentasMicroservicioApplication.class, args);
  }
}
