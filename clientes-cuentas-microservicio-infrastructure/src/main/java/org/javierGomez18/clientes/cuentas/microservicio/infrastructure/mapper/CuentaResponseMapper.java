package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.CuentaRS;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CuentaResponseMapper {
    
    @Mapping(source = "total", target = "total")
    CuentaRS toResponse(CuentaBancaria domain);
    
    List<CuentaRS> toResponseList(List<CuentaBancaria> domains);
}

