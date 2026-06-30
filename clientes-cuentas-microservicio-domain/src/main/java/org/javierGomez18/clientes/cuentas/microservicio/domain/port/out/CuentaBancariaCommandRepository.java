package org.javierGomez18.clientes.cuentas.microservicio.domain.port.out;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;

public interface CuentaBancariaCommandRepository {
    void addCuenta(Cliente cliente, CuentaBancaria cuenta);
    void updateCuenta(CuentaBancaria cuenta);
}