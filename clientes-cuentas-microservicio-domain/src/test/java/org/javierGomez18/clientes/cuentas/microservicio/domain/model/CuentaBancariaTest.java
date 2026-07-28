package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CuentaBancariaTest {

  @Test
  void ingreso_increasesTotal() {
    CuentaBancaria cuenta = new CuentaBancaria(1L, "11111111A", "NORMAL", 100.0f);
    cuenta.aplicarMovimiento(TipoMovimiento.INGRESO, 50.0f);
    assertEquals(150.0f, cuenta.getTotal());
  }

  @Test
  void retirada_decreasesTotal() {
    CuentaBancaria cuenta = new CuentaBancaria(1L, "11111111A", "NORMAL", 200.0f);
    cuenta.aplicarMovimiento(TipoMovimiento.RETIRADA, 75.5f);
    assertEquals(124.5f, cuenta.getTotal(), 0.0001);
  }

  @Test
  void transferencia_decreasesTotal() {
    CuentaBancaria cuenta = new CuentaBancaria(1L, "11111111A", "NORMAL", 300.0f);
    cuenta.aplicarMovimiento(TipoMovimiento.TRANSFERENCIA, 100.0f);
    assertEquals(200.0f, cuenta.getTotal(), 0.0001);
  }
}
