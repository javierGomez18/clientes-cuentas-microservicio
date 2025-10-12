package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mappers;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.DTO.Entities.ClienteEntity;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.NewCuentaDTORQ;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {ToCuentaBancariaDomainMapper.class}
)
public abstract class ToClienteDomainMapper {

    @Autowired
    protected ToCuentaBancariaDomainMapper toCuentaBancariaDomainMapper;

    public abstract Cliente toClienteDomain(ClienteEntity cliente);
    public abstract List<Cliente> toClienteDomainLst(List<ClienteEntity> cliente);

    @Mappings({
            @Mapping(source = "dniCliente", target = "dni"),
            @Mapping(source = "nombre", target = "nombre"),
            @Mapping(source = "apellido1", target = "apellido1"),
            @Mapping(source = "apellido2", target = "apellido2")
    })
    public abstract Cliente toClienteDomain(NewCuentaDTORQ cuenta);

    @AfterMapping
    protected void addCuentaToCliente(NewCuentaDTORQ dto, @MappingTarget Cliente cliente) {
        CuentaBancaria cuenta = toCuentaBancariaDomainMapper.toCuentaDomain(dto);
        cliente.setCuentas(List.of(cuenta));
    }
}
