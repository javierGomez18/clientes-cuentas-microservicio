package org.javierGomez18.clientes.cuentas.microservicio.domain.port.in;

public interface CreateCuentaUseCase {
  void createCuenta(CreateCuentaCommand command);

  record CreateCuentaCommand(String dniCliente, String tipoCuenta, Float saldoInicial) {}
}
