package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mappers;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.DTO.Entities.CuentaBancariaEntity;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.NewCuentaDTORQ;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.UpdateCuentaDTORQ;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ToCuentaBancariaDomainMapper {

    @Mapping(source = "cliente.dni", target = "dniCliente")
    CuentaBancaria toCuentaDomain(CuentaBancariaEntity entity);

    @Mappings({
            @Mapping(source = "dniCliente", target = "dniCliente"),
            @Mapping(source = "tipoCuenta", target = "tipoCuenta"),
            @Mapping(source = "total", target = "total")
    })
    CuentaBancaria toCuentaDomain(NewCuentaDTORQ cuenta);

    @Mappings({
            @Mapping(source = "idCuenta", target = "id"),
            @Mapping(source = "updateCuentaDTORQ.total", target = "total")
    })
    CuentaBancaria toCuentaDomain(Long idCuenta, UpdateCuentaDTORQ updateCuentaDTORQ);
}
