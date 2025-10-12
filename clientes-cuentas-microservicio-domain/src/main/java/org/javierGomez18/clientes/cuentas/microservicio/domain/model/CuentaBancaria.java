package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CuentaBancaria {
    private Long id;
    private String dniCliente;
    private String tipoCuenta;
    private Float total;
}
