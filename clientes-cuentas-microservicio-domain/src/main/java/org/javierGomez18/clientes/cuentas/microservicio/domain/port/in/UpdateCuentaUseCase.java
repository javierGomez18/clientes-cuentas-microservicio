package org.javierGomez18.clientes.cuentas.microservicio.domain.port.in;

/**
 * Puerto de entrada: Actualizar cuenta
 */
public interface UpdateCuentaUseCase {
    void updateCuenta(UpdateCuentaCommand command);

    record UpdateCuentaCommand(
        Long cuentaId,
        Float nuevoSaldo
    ) {}
}

