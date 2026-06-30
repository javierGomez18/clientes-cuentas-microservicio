package org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cuenta;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CuentaUnprocessableEntityException extends RuntimeException {
    public enum Tipo {
        CUENTA_JUNIOR_MAYOR_EDAD,
    }

    private final Tipo tipo;

    public CuentaUnprocessableEntityException(Long id, Integer edad) {
        super("No se puede crear una cuenta junior para un cliente mayor de edad.");
        this.tipo = Tipo.CUENTA_JUNIOR_MAYOR_EDAD;
    }
}
