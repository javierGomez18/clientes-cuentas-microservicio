package org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cuenta;

import lombok.Getter;

@Getter
public class CuentaConflictException extends RuntimeException {
    public enum Tipo {
        NO_CERRABLE,
    }

    private final Tipo tipo;

    public CuentaConflictException(Long id, Tipo tipo) {
        super("La cuenta con id "+id+" no puede cerrarse porque su saldo no es cero.");
        this.tipo = tipo;
    }
}
