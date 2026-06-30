package org.javierGomez18.clientes.cuentas.microservicio.domain.port.out;

import java.util.Optional;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;

public interface MovimientoCommandRepository {
  Optional<Movimiento> addMovimiento(Movimiento movimiento);
}
