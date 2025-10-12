package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.config;

import org.javierGomez18.clientes.cuentas.microservicio.create.CreateCuentasClienteUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.repository.ClientesQueryRepository;
import org.javierGomez18.clientes.cuentas.microservicio.domain.repository.CuentaBancariaCommandRepository;
import org.javierGomez18.clientes.cuentas.microservicio.find.FindCuentasClientesUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.update.UpdateCuentasClientesUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public FindCuentasClientesUseCase findCuentasClientesUseCase(ClientesQueryRepository  clientesRepository){
        return new FindCuentasClientesUseCase(clientesRepository);
    }

    @Bean
    public UpdateCuentasClientesUseCase updateCuentasClientesUseCase(CuentaBancariaCommandRepository cuentaBancariaCommandRepository){
        return new UpdateCuentasClientesUseCase(cuentaBancariaCommandRepository);
    }

    @Bean
    public CreateCuentasClienteUseCase createCuentasClienteUseCase(CuentaBancariaCommandRepository  cuentaBancariaCommandRepository){
        return new CreateCuentasClienteUseCase(cuentaBancariaCommandRepository);
    }
}
