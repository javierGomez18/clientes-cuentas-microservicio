package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ClienteEntityTest {

  @Test
  void gettersAndSetters() {
    ClienteEntity e = new ClienteEntity();
    e.setDni("11111111A");
    e.setNombre("Ana");
    e.setFechaNacimiento(LocalDate.of(1995, 5, 5));

    assertEquals("11111111A", e.getDni());
    assertEquals("Ana", e.getNombre());
    assertEquals(LocalDate.of(1995, 5, 5), e.getFechaNacimiento());
  }
}
