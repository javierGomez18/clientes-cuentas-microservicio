package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class CuentaEntityMapperUnitTest {

  private final CuentaEntityMapper mapper = Mappers.getMapper(CuentaEntityMapper.class);

  @Test
  void toDomain_and_back() {
    CuentaBancariaEntity e = new CuentaBancariaEntity();
    e.setId(10L);
    e.setTipoCuenta("PREMIUM");
    e.setTotal(123.45f);

    CuentaBancaria d = mapper.toDomain(e);
    assertEquals(123.45f, d.getTotal());

    CuentaBancariaEntity e2 = mapper.toEntity(d);
    assertNotNull(e2);
  }
}
