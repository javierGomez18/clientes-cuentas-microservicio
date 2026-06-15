package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Movimiento(Long id,
                         @NonNull LocalDate fechaOperacion,
                         @NonNull TipoMovimiento tipo,
                         @NonNull BigDecimal importe,
                         BigDecimal saldoResultante,
                         @NonNull Long cuentaId,
                         String descripcion) {
}
