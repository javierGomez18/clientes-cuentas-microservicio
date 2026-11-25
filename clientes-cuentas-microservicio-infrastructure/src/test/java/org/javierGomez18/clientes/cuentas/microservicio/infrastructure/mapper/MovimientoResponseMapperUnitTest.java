package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.TipoMovimiento;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.MovimientoRS;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.TipoMovimientoEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mapstruct.factory.Mappers;

class MovimientoResponseMapperUnitTest {

  private final MovimientoResponseMapper mapper = Mappers.getMapper(MovimientoResponseMapper.class);

  /** Todos los tipos del dominio deben llegar al DTO; antes TRANSFERENCIA salía como null. */
  @ParameterizedTest
  @EnumSource(TipoMovimiento.class)
  void mapeaTodosLosTiposDeMovimiento(TipoMovimiento tipo) {
    Movimiento m =
        Movimiento.builder()
            .id(1L)
            .fechaOperacion(LocalDate.of(2024, 1, 2))
            .tipo(tipo)
            .importe(new BigDecimal("50.00"))
            .cuentaId(5L)
            .build();

    MovimientoRS rs = mapper.toResponse(m);

    assertNotNull(rs.getTipoMovimiento(), "tipoMovimiento no debe ser null para " + tipo);
    assertEquals(TipoMovimientoEnum.valueOf(tipo.name()), rs.getTipoMovimiento());
  }

  @Test
  void tipoNuloSeMapeaANulo() {
    assertNull(mapper.map((TipoMovimiento) null));
  }
}
