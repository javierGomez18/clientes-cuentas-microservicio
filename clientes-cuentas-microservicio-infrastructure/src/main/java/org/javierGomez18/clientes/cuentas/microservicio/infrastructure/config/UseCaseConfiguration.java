package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.config;

import org.javierGomez18.clientes.cuentas.microservicio.application.create.CreateCuentaService;
import org.javierGomez18.clientes.cuentas.microservicio.application.create.CreateMovimientoService;
import org.javierGomez18.clientes.cuentas.microservicio.application.find.FindCuentaService;
import org.javierGomez18.clientes.cuentas.microservicio.application.find.FindMovimientoService;
import org.javierGomez18.clientes.cuentas.microservicio.application.update.UpdateCuentaService;

import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.*;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.*;
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

    @Bean
    public CreateMovimientoUseCase createMovimientoUseCase(CuentaBancariaCommandRepository cuentaCommandRepository,
                                                           CuentaBancariaQueryRepository cuentaQueryRepository,
                                                           MovimientoCommandRepository movimientoRepository) {
        return new CreateMovimientoService(cuentaCommandRepository, cuentaQueryRepository, movimientoRepository);
    }
}

