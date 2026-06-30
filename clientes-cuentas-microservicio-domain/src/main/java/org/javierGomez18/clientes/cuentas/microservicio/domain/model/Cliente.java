package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

  private String dni;
  private String nombre;
  private String apellido1;
  private String apellido2;
  private LocalDate fechaNacimiento;
  private List<CuentaBancaria> cuentas;
}
