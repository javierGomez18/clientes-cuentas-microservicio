package org.javierGomez18.clientes.cuentas.microservicio.domain.exception.movimiento;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class MovimientoUnprocessableEntityException extends RuntimeException {
    public enum Tipo {
        SALDO_INSUFICIENTE
    }

    private final Tipo tipo;

    public MovimientoUnprocessableEntityException(Long id, BigDecimal saldo) {
        super("Saldo insuficiente para la cuenta: "+ id);
        this.tipo = Tipo.SALDO_INSUFICIENTE;
    }
}
