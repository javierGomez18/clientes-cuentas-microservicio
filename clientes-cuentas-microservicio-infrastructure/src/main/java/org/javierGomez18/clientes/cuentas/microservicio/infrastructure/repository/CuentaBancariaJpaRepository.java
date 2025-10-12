package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.repository;

import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.DTO.Entities.CuentaBancariaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CuentaBancariaJpaRepository extends JpaRepository<CuentaBancariaEntity, Long> {

    List<CuentaBancariaEntity> findByClienteDni(String dni);
}
