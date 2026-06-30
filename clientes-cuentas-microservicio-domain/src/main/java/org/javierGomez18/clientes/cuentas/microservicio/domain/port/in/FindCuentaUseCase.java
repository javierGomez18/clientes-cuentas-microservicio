package org.javierGomez18.clientes.cuentas.microservicio.domain.port.in;

import java.util.List;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;

public interface FindCuentaUseCase {
  Cliente findClienteByDni(String dni);

  List<Cliente> findAllClientes();

  List<Cliente> findClientesAdultos();

  List<Cliente> findClientesByTotalGreaterThan(Float cantidad);

  record FindCuentaQuery(String dni) {}
}
