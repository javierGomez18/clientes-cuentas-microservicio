package org.javierGomez18.clientes.cuentas.microservicio.domain.repository;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;

public interface CuentaBancariaCommandRepository {
    void addCuenta(Cliente c);
    void updateCuenta(CuentaBancaria c);
}
