package org.javierGomez18.clientes.cuentas.microservicio.domain.port.in;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageResult;

public interface FindMovimientoUseCase {
    PageResult<Movimiento> findByCuentaId(Long cuentaId, int page, int size, String sort);

    record FindMovimientoQuery(Long cuentaId, int page, int size, String sort) {}
}

