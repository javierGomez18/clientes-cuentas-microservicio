package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class MovimientoEntityTest {

  @Test
  void prePersist_setsCreateAt() {
    MovimientoEntity e = new MovimientoEntity();
    assertNull(e.getCreateAt());
    e.prePersist();
    assertNotNull(e.getCreateAt());
    assertEquals(LocalDate.now(), e.getCreateAt());
  }
}
