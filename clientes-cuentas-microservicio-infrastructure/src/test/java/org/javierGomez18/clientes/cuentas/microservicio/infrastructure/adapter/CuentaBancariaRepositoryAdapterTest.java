package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.adapter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.ClienteEntityMapper;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.CuentaEntityMapper;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.repository.ClienteJpaRepository;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.repository.CuentaBancariaJpaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CuentaBancariaRepositoryAdapterTest {

  @Mock CuentaBancariaJpaRepository cuentaBancariaJpaRepository;

  @Mock ClienteJpaRepository clienteJpaRepository;

  @Mock ClienteEntityMapper clienteEntityMapper;

  @Mock CuentaEntityMapper cuentaEntityMapper;

  @InjectMocks CuentaBancariaRepositoryAdapter adapter;

  @Test
  void addCuenta_throwsOnNullClienteOrDni() {
    assertThrows(NullPointerException.class, () -> adapter.addCuenta(null, new CuentaBancaria()));
    var cliente = new Cliente();
    assertThrows(
        IllegalArgumentException.class, () -> adapter.addCuenta(cliente, new CuentaBancaria()));
  }

  @Test
  void updateCuenta_validations() {
    assertThrows(IllegalArgumentException.class, () -> adapter.updateCuenta(new CuentaBancaria()));
    var cuenta = new CuentaBancaria();
    cuenta.setId(1L);
    cuenta.setTotal(-10.0f);
    assertThrows(IllegalArgumentException.class, () -> adapter.updateCuenta(cuenta));
  }

  @Test
  void findCuenta_delegatesAndMaps() {
    var entity = new CuentaBancariaEntity();
    entity.setId(1L);
    var domain = new CuentaBancaria(1L, "11111111A", "NORMAL", 100.0f);

    when(cuentaBancariaJpaRepository.findById(1L)).thenReturn(Optional.of(entity));
    when(cuentaEntityMapper.toDomain(entity)).thenReturn(domain);

    var res = adapter.findCuenta(1L);
    assertTrue(res.isPresent());
    assertEquals(1L, res.get().getId());
  }
}
