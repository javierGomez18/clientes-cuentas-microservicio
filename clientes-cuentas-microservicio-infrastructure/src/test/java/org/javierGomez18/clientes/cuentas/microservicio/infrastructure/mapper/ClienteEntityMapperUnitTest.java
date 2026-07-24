package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.ClienteEntity;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class ClienteEntityMapperUnitTest {

  private final ClienteEntityMapper mapper = Mappers.getMapper(ClienteEntityMapper.class);

  @Test
  void entity_to_domain_and_list() throws Exception {
    // ensure nested mapper is available (mapstruct with componentModel spring requires DI)
    try {
      var impl = mapper;
      var field = impl.getClass().getDeclaredField("cuentaEntityMapper");
      field.setAccessible(true);
      field.set(impl, Mappers.getMapper(CuentaEntityMapper.class));
    } catch (NoSuchFieldException ignored) {
      // field may be named differently in generated impl; ignore if not present
    }

    ClienteEntity e = new ClienteEntity();
    e.setDni("11111111A");
    CuentaBancariaEntity ce = new CuentaBancariaEntity();
    ce.setId(2L);
    e.setCuentas(List.of(ce));

    Cliente d = mapper.toDomain(e);
    assertEquals("11111111A", d.getDni());
    assertNotNull(mapper.toDomainList(List.of(e)));
  }
}
