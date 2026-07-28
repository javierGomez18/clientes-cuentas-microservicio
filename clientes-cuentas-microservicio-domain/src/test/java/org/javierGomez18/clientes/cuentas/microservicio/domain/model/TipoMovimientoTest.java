package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TipoMovimientoTest {

  @Test
  void fromString_acceptsDifferentCasesAndWhitespace() {
    assertEquals(TipoMovimiento.INGRESO, TipoMovimiento.fromString(" ingreso "));
    assertEquals(TipoMovimiento.RETIRADA, TipoMovimiento.fromString("retirada"));
    assertEquals(TipoMovimiento.TRANSFERENCIA, TipoMovimiento.fromString("TRANSFERENCIA"));
  }

  @Test
  void fromString_null_throws() {
    assertThrows(IllegalArgumentException.class, () -> TipoMovimiento.fromString(null));
  }

  @Test
  void fromString_invalid_throws() {
    assertThrows(IllegalArgumentException.class, () -> TipoMovimiento.fromString("INVALID"));
  }
}
