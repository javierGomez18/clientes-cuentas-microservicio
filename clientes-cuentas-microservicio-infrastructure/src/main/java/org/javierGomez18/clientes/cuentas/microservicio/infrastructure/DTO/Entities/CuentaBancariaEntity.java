package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.DTO.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CUENTA_BANCARIA")
@Getter
@Setter
public class CuentaBancariaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DNI_CLIENTE", nullable = false)
    private ClienteEntity cliente;
    @Column(name = "TIPO_CUENTA", nullable = false)
    private String tipoCuenta;
    @Column(name = "TOTAL", nullable = false)
    private Float total;
}
