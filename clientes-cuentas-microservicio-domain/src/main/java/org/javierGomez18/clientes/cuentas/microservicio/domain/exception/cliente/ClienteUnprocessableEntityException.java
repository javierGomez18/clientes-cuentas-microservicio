package org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cliente;

public class ClienteUnprocessableEntityException extends RuntimeException {
    public enum Tipo {
        FECHA_NACIMIENTO_FUTURA
    }

    private final Tipo tipo;

    public ClienteUnprocessableEntityException(String dni) {
        super("No se puede crear un cliente cuya fecha de nacimiento es posterior a la fecha actual.");
        this.tipo = Tipo.FECHA_NACIMIENTO_FUTURA;
    }
}
