package org.javierGomez18.clientes.cuentas.microservicio.domain.port.in;

/**
 * Puerto de entrada: Crear cuenta bancaria
 */
public interface CreateCuentaUseCase {
    void createCuenta(CreateCuentaCommand command);

    record CreateCuentaCommand(
        String dniCliente,
        String tipoCuenta,
        Float saldoInicial
    ) {}
}

