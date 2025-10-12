package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mappers;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.DTO.Entities.ClienteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ToCuentaBancariaEntityMapper.class})
public interface ToClienteEntityMapper {

    ClienteEntity toClienteEntity(Cliente cliente);
}
