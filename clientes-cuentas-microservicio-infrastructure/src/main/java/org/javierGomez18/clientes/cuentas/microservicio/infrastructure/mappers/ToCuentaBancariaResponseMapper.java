package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mappers;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.CuentaDTORS;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ToCuentaBancariaResponseMapper {

    CuentaDTORS toCuentaResponse(CuentaBancaria c);

    List<CuentaDTORS> toCuentaResponseLst(List<CuentaBancaria> lstCuenta);
}
