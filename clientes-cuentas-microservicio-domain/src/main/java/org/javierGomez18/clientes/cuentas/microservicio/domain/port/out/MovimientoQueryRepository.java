package org.javierGomez18.clientes.cuentas.microservicio.domain.port.out;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageRequest;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageResult;

import java.util.List;

/**
 * Puerto de salida: Repositorio de consultas de movimientos
 */
public interface MovimientoQueryRepository {
    PageResult<Movimiento> findByCuentaId(Long cuentaId, PageRequest pageRequest);
}

