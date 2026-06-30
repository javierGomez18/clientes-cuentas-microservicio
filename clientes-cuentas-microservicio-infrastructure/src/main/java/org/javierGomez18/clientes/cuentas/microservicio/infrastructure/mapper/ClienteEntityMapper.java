package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper;

import java.util.List;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.ClienteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** Mapper: ClienteEntity <-> Cliente (Domain) */
@Mapper(
    componentModel = "spring",
    uses = {CuentaEntityMapper.class})
public interface ClienteEntityMapper {

  @Mapping(source = "dni", target = "dni")
  @Mapping(source = "cuentas", target = "cuentas")
  Cliente toDomain(ClienteEntity entity);

  @Mapping(source = "dni", target = "dni")
  @Mapping(source = "cuentas", target = "cuentas")
  ClienteEntity toEntity(Cliente domain);

  List<Cliente> toDomainList(List<ClienteEntity> entities);

  List<ClienteEntity> toEntityList(List<Cliente> domains);
}
