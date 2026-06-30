package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CLIENTE")
@Getter
@Setter
public class ClienteEntity {

  @Id
  @Column(name = "DNI", length = 9)
  private String dni;

  @Column(name = "NOMBRE", nullable = true, length = 100)
  private String nombre;

  @Column(name = "APELLIDO1", nullable = true, length = 100)
  private String apellido1;

  @Column(name = "APELLIDO2", nullable = true, length = 100)
  private String apellido2;

  @Column(name = "FECHA_NACIMIENTO", nullable = true, length = 100)
  private LocalDate fechaNacimiento;

  @OneToMany(mappedBy = "cliente", orphanRemoval = true)
  private List<CuentaBancariaEntity> cuentas;
}
