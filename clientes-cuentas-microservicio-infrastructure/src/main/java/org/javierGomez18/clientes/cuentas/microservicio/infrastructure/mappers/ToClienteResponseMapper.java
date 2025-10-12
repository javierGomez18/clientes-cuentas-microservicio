package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mappers;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.ClienteDTORS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ToCuentaBancariaResponseMapper.class})
public interface ToClienteResponseMapper {

    @Mapping(source = "dni", target = "dniCliente")
    ClienteDTORS toClienteResponse(Cliente c);

    List<ClienteDTORS> toClienteResponseLst(List<Cliente> lstCliente);
}
