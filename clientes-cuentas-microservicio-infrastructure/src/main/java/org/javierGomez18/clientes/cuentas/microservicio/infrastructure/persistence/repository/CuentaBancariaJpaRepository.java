package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.repository;

import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaBancariaJpaRepository extends JpaRepository<CuentaBancariaEntity, Long> {
    List<CuentaBancariaEntity> findByClienteDni(String dniCliente);
}

