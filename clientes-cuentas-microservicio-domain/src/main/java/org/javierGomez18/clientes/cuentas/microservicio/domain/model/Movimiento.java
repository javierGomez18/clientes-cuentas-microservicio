package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movimiento {

  private Long id;

  @NonNull private LocalDate fechaOperacion;

  @NonNull private TipoMovimiento tipo;

  @NonNull private BigDecimal importe;

  private BigDecimal saldoResultante;

  @NonNull private Long cuentaId;

  private String descripcion;
}
