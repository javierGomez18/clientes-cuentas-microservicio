package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.repository;

import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.DTO.Entities.ClienteEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ClienteJpaRepository extends JpaRepository<ClienteEntity,String> {

    @EntityGraph(attributePaths = "cuentas")
    List<ClienteEntity> findAll();

    @EntityGraph(attributePaths = "cuentas")
    Optional<ClienteEntity> findByDni(String dni);

    List<ClienteEntity> findClienteEntitiesByFechaNacimientoIsLessThanEqual(LocalDate l);

    @Query("""
        SELECT c
        FROM ClienteEntity c
        JOIN c.cuentas cu
        GROUP BY c
        HAVING SUM(cu.total) > :total
    """)
    List<ClienteEntity> findClientesByTotalCuentas(@Param("total") BigDecimal total);
}
