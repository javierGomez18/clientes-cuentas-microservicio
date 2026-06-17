package org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cliente;

import lombok.Getter;

@Getter
public class ClienteConflictException extends RuntimeException {
    public enum Tipo {
        TIENE_CUENTAS_ABIERTAS
    }

    private final Tipo tipo;

    public ClienteConflictException(String dni) {
        super("El cliente con id " + dni + " no puede eliminarse porque tiene cuentas abiertas.");
        this.tipo = Tipo.TIENE_CUENTAS_ABIERTAS;
    }
}
