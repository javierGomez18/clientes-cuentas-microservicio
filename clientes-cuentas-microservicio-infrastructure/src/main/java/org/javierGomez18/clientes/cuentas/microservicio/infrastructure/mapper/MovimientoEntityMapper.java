package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.TipoMovimiento;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.MovimientoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper: MovimientoEntity <-> Movimiento (Domain)
 */
@Mapper(componentModel = "spring")
public interface MovimientoEntityMapper {
    
    @Mapping(source="createAt",target="fechaOperacion")
    @Mapping(source = "cuenta.id", target = "cuentaId")
    Movimiento toDomain(MovimientoEntity entity);

    @Mapping(source="fechaOperacion",target="createAt")
    MovimientoEntity toEntity(Movimiento domain);
    
    List<Movimiento> toDomainList(List<MovimientoEntity> entities);
    List<MovimientoEntity> toEntityList(List<Movimiento> domains);

    default TipoMovimiento map(String value) {
        return value == null ? null : TipoMovimiento.valueOf(value);
    }
}

