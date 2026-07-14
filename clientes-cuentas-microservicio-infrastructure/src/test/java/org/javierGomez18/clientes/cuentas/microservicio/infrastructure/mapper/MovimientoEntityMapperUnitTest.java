package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.MovimientoEntity;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovimientoEntityMapperUnitTest {

    private final MovimientoEntityMapper mapper = Mappers.getMapper(MovimientoEntityMapper.class);

    @Test
    void entity_to_domain_mapsFields() {
        MovimientoEntity e = new MovimientoEntity();
        e.setId(1L);
        e.setCreateAt(LocalDate.of(2020,1,2));
        e.setTipo("INGRESO");
        e.setImporte(50.0f);
        CuentaBancariaEntity c = new CuentaBancariaEntity();
        c.setId(5L);
        e.setCuenta(c);

        Movimiento d = mapper.toDomain(e);
        assertEquals(5L, d.getCuentaId().longValue());
        assertEquals(LocalDate.of(2020,1,2), d.getFechaOperacion());
    }
}
