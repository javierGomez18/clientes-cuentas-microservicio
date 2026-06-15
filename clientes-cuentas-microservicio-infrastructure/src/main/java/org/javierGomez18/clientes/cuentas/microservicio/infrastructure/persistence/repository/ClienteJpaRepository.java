package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.repository;

import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA: Cliente
 */
@Repository
public interface ClienteJpaRepository extends JpaRepository<ClienteEntity, String> {
    Optional<ClienteEntity> findByDni(String dni);

    List<ClienteEntity> findByFechaNacimientoBefore(LocalDate fecha);
    
    @Query("SELECT c FROM ClienteEntity c JOIN c.cuentas cu GROUP BY c HAVING SUM(cu.total) > :total")
    List<ClienteEntity> findClientesByTotalCuentas(@Param("total") BigDecimal total);
}

