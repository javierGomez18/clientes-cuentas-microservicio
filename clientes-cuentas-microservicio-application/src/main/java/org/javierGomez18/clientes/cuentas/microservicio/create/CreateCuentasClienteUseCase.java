package org.javierGomez18.clientes.cuentas.microservicio.create;

import lombok.RequiredArgsConstructor;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.repository.CuentaBancariaCommandRepository;

@RequiredArgsConstructor
public class CreateCuentasClienteUseCase {

    private final CuentaBancariaCommandRepository cuentaBancariaCommandRepository;

    public void createCuentaCliente(Cliente c){
        cuentaBancariaCommandRepository.addCuenta(c);
    }
}
