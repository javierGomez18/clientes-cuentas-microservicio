package org.javierGomez18.clientes.cuentas.microservicio.domain.port.out;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;

import java.util.Optional;

public interface MovimientoCommandRepository {
    Optional<Movimiento> addMovimiento(Movimiento movimiento);
}
