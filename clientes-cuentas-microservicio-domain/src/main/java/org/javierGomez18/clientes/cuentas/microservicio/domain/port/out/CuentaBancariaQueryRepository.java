package org.javierGomez18.clientes.cuentas.microservicio.domain.port.out;

import java.util.Optional;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;

public interface CuentaBancariaQueryRepository {
  Optional<CuentaBancaria> findCuenta(Long idCuenta);
}
