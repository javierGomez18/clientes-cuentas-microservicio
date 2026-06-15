package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageRequest;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageResult;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.MovimientoQueryRepository;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.MovimientoEntityMapper;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.MovimientoEntity;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.repository.MovimientoJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MovimientoRepositoryAdapter implements MovimientoQueryRepository {

    private final MovimientoJpaRepository movimientoJpaRepository;
    private final MovimientoEntityMapper movimientoEntityMapper;

    @Override
    public PageResult<Movimiento> findByCuentaId(Long cuentaId, PageRequest pageRequest) {

        if (cuentaId == null) {
            log.error("Cuenta o ID de cuenta nulo");
            throw new IllegalArgumentException("Cuenta e ID no pueden ser nulos");
        }

        Page<MovimientoEntity> pageResult = movimientoJpaRepository
                .findByCuentaId(cuentaId, org.springframework.data.domain.PageRequest.of(
                        pageRequest.page(),
                        pageRequest.size(),
                        pageRequest.descending()
                                ? Sort.by(Sort.Direction.DESC, "createAt")
                                : Sort.by(Sort.Direction.ASC, "createAt")
                ));

        return PageResult.<Movimiento>builder()
                .page(pageResult.getNumber())
                .size(pageResult.getSize())
                .totalElements(pageResult.getTotalElements())
                .totalPages(pageResult.getTotalPages())
                .content(pageResult.getContent().stream().map(movimientoEntityMapper::toDomain).toList())
                .build();
    }
}
