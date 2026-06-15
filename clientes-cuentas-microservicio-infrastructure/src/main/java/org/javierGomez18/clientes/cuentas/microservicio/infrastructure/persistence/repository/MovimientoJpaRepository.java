package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.repository;

import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.MovimientoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovimientoJpaRepository extends PagingAndSortingRepository<MovimientoEntity, Long> {
    Page<MovimientoEntity> findByCuentaId(Long cuentaId, Pageable pageable);
}

