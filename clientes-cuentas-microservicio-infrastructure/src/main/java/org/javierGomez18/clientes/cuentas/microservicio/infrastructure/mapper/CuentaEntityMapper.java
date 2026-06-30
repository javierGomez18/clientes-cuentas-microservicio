package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper;

import java.util.List;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Mapper: CuentaBancariaEntity <-> CuentaBancaria (Domain) */
@Mapper(
    componentModel = "spring",
    uses = {MovimientoEntityMapper.class})
public interface CuentaEntityMapper {

  @Mapping(source = "total", target = "total")
  CuentaBancaria toDomain(CuentaBancariaEntity entity);

  @Mapping(target = "cliente", ignore = true)
  CuentaBancariaEntity toEntity(CuentaBancaria domain);

  List<CuentaBancaria> toDomainList(List<CuentaBancariaEntity> entities);

  List<CuentaBancariaEntity> toEntityList(List<CuentaBancaria> domains);
}
