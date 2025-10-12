package org.javierGomez18.clientes.cuentas.microservicio.domain.excepciones;

import lombok.Getter;
import org.javierGomez18.clientes.cuentas.microservicio.domain.constants.ErrorMessages;

@Getter
public class ClienteCuentasNotFoundException extends RuntimeException{

    public enum Tipo {
        BY_DNI, BY_CUENTA_ID, BY_TOTAL, BY_EDAD
    }

    private final Tipo tipo;

    public ClienteCuentasNotFoundException(String dni) {
        super(ErrorMessages.NOT_FOUND_BY_DNI_ERROR + dni);
        this.tipo = Tipo.BY_DNI;
    }

    public ClienteCuentasNotFoundException(Long id) {
        super(ErrorMessages.NOT_FOUND_BY_CUENTA_ID_ERROR + id);
        this.tipo = Tipo.BY_CUENTA_ID;
    }

    public ClienteCuentasNotFoundException() {
        super(ErrorMessages.NOT_FOUND_BY_EDAD);
        this.tipo = Tipo.BY_EDAD;
    }

    public ClienteCuentasNotFoundException(Float total) {
        super(ErrorMessages.NOT_FOUND_BY_TOTAL_CUENTAS + total);
        this.tipo = Tipo.BY_TOTAL;
    }
}
