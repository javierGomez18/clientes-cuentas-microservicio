package org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cuenta;

import lombok.Getter;

@Getter
public class CuentaNotFoundException extends RuntimeException {
    public enum Tipo {
        BY_CUENTA_ID
    }

    private final Tipo tipo;

    public CuentaNotFoundException(Long id) {
        super("Cuenta no encontrada con ID de cuenta: " + id);
        this.tipo = Tipo.BY_CUENTA_ID;
    }
}
