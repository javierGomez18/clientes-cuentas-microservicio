package org.javierGomez18.clientes.cuentas.microservicio.update;

import lombok.RequiredArgsConstructor;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.domain.repository.CuentaBancariaCommandRepository;

@RequiredArgsConstructor
public class UpdateCuentasClientesUseCase {

    private final CuentaBancariaCommandRepository cuentaBancariaCommandRepository;

    public void updateCuenta(CuentaBancaria c){
        cuentaBancariaCommandRepository.updateCuenta(c);
    }
}
