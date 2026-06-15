package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.ClienteRS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper: Cliente (Domain) -> ClienteResponse (DTO)
 */
@Mapper(componentModel = "spring", uses = {CuentaResponseMapper.class})
public interface ClienteResponseMapper {
    
    @Mapping(source = "dni", target = "dniCliente")
    @Mapping(source = "cuentas", target = "cuentas")
    ClienteRS toResponse(Cliente domain);
    
    List<ClienteRS> toResponseList(List<Cliente> domains);
}

