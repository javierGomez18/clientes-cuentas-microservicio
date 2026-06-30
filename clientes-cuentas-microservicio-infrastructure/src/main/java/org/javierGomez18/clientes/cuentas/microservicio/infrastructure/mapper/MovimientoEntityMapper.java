package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.TipoMovimiento;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.CuentaBancariaEntity;
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
    @Mapping(source = "cuentaId", target = "cuenta")
    MovimientoEntity toEntity(Movimiento domain);
    
    List<Movimiento> toDomainList(List<MovimientoEntity> entities);
    List<MovimientoEntity> toEntityList(List<Movimiento> domains);

    default TipoMovimiento map(String value) {
        return value == null ? null : TipoMovimiento.valueOf(value);
    }

    default CuentaBancariaEntity map(Long idCuenta) {
        if (idCuenta == null) return null;
        CuentaBancariaEntity c = new CuentaBancariaEntity();
        c.setId(idCuenta);
        return c;
    }
}

