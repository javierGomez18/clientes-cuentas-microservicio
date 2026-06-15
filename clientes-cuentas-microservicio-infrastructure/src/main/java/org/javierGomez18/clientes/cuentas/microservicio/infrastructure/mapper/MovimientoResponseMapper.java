package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.TipoMovimiento;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoRS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * Mapper: Movimiento (Domain) -> MovimientoResponse (DTO)
 */
@Mapper(componentModel = "spring")
public interface MovimientoResponseMapper {

    @Mapping(target = "tipoMovimiento", source = "tipo")
    MovimientoRS toResponse(Movimiento domain);
    
    List<MovimientoRS> toResponseList(List<Movimiento> domains);

    default OffsetDateTime map(LocalDate date) {
        return date != null
                ? date.atStartOfDay().atOffset(ZoneOffset.UTC)
                : null;
    }

    default MovimientoRS.TipoMovimientoEnum map(TipoMovimiento value) {
        if (value == null) return null;

        return switch (value) {
            case INGRESO -> MovimientoRS.TipoMovimientoEnum.INGRESO;
            case RETIRADA  -> MovimientoRS.TipoMovimientoEnum.RETIRADA;
            default -> null;
        };
    }
}

