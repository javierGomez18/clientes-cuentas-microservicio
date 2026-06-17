package org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cliente;

import lombok.Getter;

@Getter
public class ClienteNotFoundException extends RuntimeException {

    public enum Tipo {
        BY_DNI, BY_CUENTA_ID, BY_TOTAL, BY_EDAD
    }

    private final Tipo tipo;

    public ClienteNotFoundException(String dni) {
        super("Cliente no encontrado con DNI: " + dni);
        this.tipo = Tipo.BY_DNI;
    }

    public ClienteNotFoundException(Long id) {
        super("Cliente no encontrado con ID de cuenta: " + id);
        this.tipo = Tipo.BY_CUENTA_ID;
    }

    public ClienteNotFoundException() {
        super("No se encontraron clientes adultos");
        this.tipo = Tipo.BY_EDAD;
    }

    public ClienteNotFoundException(Float total) {
        super("No se encontraron clientes con total mayor a: " + total);
        this.tipo = Tipo.BY_TOTAL;
    }
}
