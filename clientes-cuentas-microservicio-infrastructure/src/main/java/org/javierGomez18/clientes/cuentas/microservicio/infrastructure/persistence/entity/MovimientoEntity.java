package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "MOVIMIENTO")
@Getter
@Setter
public class MovimientoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "CREATE_AT")
    LocalDate createAt;
    @Column(name = "TIPO", nullable = false)
    String tipo;
    @Column(name = "IMPORTE", nullable = false)
    Float importe;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUENTA_ID", nullable = false)
    CuentaBancariaEntity cuenta;
    @Column(name = "DESCRIPCION")
    String descripcion;

    @PrePersist
    public void prePersist() {
        this.createAt = LocalDate.now();
    }
}

