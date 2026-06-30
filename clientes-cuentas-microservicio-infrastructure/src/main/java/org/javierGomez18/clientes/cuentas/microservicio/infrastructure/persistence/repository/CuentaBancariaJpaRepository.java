package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.repository;

import java.util.List;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaBancariaJpaRepository extends JpaRepository<CuentaBancariaEntity, Long> {
  List<CuentaBancariaEntity> findByClienteDni(String dniCliente);
}
