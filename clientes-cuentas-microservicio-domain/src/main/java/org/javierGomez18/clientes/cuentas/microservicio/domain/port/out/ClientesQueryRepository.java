package org.javierGomez18.clientes.cuentas.microservicio.domain.port.out;

import java.util.List;
import java.util.Optional;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;

/** Puerto de salida: Repositorio de consultas de clientes */
public interface ClientesQueryRepository {
  List<Cliente> findAll();

  Optional<Cliente> findByDni(String dni);

  List<Cliente> findAdultos();

  List<Cliente> findByTotalGreaterThan(Float total);
}
