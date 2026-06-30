package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

public enum TipoMovimiento {
  INGRESO,
  RETIRADA,
  TRANSFERENCIA;

  public static TipoMovimiento fromString(String value) {
    if (value == null) {
      throw new IllegalArgumentException("El tipo de movimiento no puede ser null");
    }

    try {
      return TipoMovimiento.valueOf(value.trim().toUpperCase());
    } catch (IllegalArgumentException ex) {
      throw new IllegalArgumentException("TipoMovimiento inválido: " + value);
    }
  }
}
