package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mappers;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.DTO.Entities.CuentaBancariaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToCuentaBancariaEntityMapper {

    CuentaBancariaEntity toCuentaEntity(CuentaBancaria entity);
}
