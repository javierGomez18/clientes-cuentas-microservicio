package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CuentaBancariaEntityTest {

  @Test
  void gettersAndSetters() {
    CuentaBancariaEntity e = new CuentaBancariaEntity();
    e.setId(7L);
    e.setTipoCuenta("NORMAL");
    e.setTotal(250.0f);

    assertEquals(7L, e.getId());
    assertEquals("NORMAL", e.getTipoCuenta());
    assertEquals(250.0f, e.getTotal());
  }
}
