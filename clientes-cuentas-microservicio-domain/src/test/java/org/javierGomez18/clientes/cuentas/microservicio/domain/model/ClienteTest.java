package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class ClienteTest {

  @Test
  void gettersAndSetters_work() {
    Cliente c = new Cliente();
    c.setDni("11111111A");
    c.setNombre("Juan");
    c.setFechaNacimiento(LocalDate.of(1990, 1, 1));
    c.setCuentas(new ArrayList<>());

    assertEquals("11111111A", c.getDni());
    assertEquals("Juan", c.getNombre());
    assertEquals(LocalDate.of(1990, 1, 1), c.getFechaNacimiento());
    assertNotNull(c.getCuentas());
  }
}
