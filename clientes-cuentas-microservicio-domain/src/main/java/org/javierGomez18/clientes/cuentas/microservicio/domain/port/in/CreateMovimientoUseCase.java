package org.javierGomez18.clientes.cuentas.microservicio.domain.port.in;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;

public interface CreateMovimientoUseCase {
  Movimiento createMovimiento(CreateMovimientoCommand command);

  record CreateMovimientoCommand(
      Long cuentaIdOrigen,
      Long cuentaIdDestino,
      String tipoMovimiento,
      Double cantidad,
      String descripcion) {}
}
