package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.config;

import org.javierGomez18.clientes.cuentas.microservicio.application.create.CreateCuentaService;
import org.javierGomez18.clientes.cuentas.microservicio.application.find.FindCuentaService;
import org.javierGomez18.clientes.cuentas.microservicio.application.movimiento.FindMovimientoService;
import org.javierGomez18.clientes.cuentas.microservicio.application.update.UpdateCuentaService;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.CreateCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindMovimientoUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.UpdateCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.ClientesQueryRepository;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.CuentaBancariaCommandRepository;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.MovimientoQueryRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public CreateCuentaUseCase createCuentaUseCase(CuentaBancariaCommandRepository cuentaRepository) {
        return new CreateCuentaService(cuentaRepository);
    }

    @Bean
    public FindCuentaUseCase findCuentaUseCase(ClientesQueryRepository clienteRepository) {
        return new FindCuentaService(clienteRepository);
    }

    @Bean
    public UpdateCuentaUseCase updateCuentaUseCase(CuentaBancariaCommandRepository cuentaRepository) {
        return new UpdateCuentaService(cuentaRepository);
    }

    @Bean
    public FindMovimientoUseCase findMovimientoUseCase(MovimientoQueryRepository movimientoRepository) {
        return new FindMovimientoService(movimientoRepository);
    }


}

