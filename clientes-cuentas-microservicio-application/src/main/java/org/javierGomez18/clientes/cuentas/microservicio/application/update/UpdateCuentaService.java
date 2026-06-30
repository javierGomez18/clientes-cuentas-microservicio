package org.javierGomez18.clientes.cuentas.microservicio.application.update;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.UpdateCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.CuentaBancariaCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UpdateCuentaService implements UpdateCuentaUseCase {

    private final CuentaBancariaCommandRepository cuentaBancariaRepository;

    @Override
    public void updateCuenta(UpdateCuentaCommand command) {
        log.info("Actualizando saldo de la cuenta: {}", command.cuentaId());
        
        CuentaBancaria cuenta = new CuentaBancaria();
        cuenta.setId(command.cuentaId());
        cuenta.setTotal(command.nuevoSaldo());
        
        cuentaBancariaRepository.updateCuenta(cuenta);
        log.info("Cuenta actualizada exitosamente");
    }
}

