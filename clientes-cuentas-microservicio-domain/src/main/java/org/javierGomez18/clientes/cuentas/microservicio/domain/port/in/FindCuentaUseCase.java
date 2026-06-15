package org.javierGomez18.clientes.cuentas.microservicio.domain.port.in;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;

import java.util.List;

public interface FindCuentaUseCase {
    Cliente findClienteByDni(String dni);
    List<Cliente> findAllClientes();
    List<Cliente> findClientesAdultos();
    List<Cliente> findClientesByTotalGreaterThan(Float cantidad);

    record FindCuentaQuery(String dni) {}
}

