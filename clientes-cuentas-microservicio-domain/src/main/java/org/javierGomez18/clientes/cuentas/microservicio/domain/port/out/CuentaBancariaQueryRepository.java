package org.javierGomez18.clientes.cuentas.microservicio.domain.port.out;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;

import java.util.Optional;

public interface CuentaBancariaQueryRepository {
    Optional<CuentaBancaria> findCuenta(Long idCuenta);
}
