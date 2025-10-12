package org.javierGomez18.clientes.cuentas.microservicio.domain.repository;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;

import java.util.List;

public interface ClientesQueryRepository {

    List<Cliente> getClientes();
    List<Cliente> getClientesAdultos();
    List<Cliente> getClientesByTotal(Float total);
    Cliente getClienteByDni(String dni);
}
