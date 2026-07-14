package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.adapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.repository.ClienteJpaRepository;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.ClienteEntityMapper;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.ClienteEntity;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ClienteRepositoryAdapterTest {

    @Mock
    ClienteJpaRepository clienteJpaRepository;

    @Mock
    ClienteEntityMapper clienteEntityMapper;

    @InjectMocks
    ClienteRepositoryAdapter adapter;

    @Test
    void findByDni_nullOrBlank_returnsEmpty() {
        assertTrue(adapter.findByDni(null).isEmpty());
        assertTrue(adapter.findByDni("").isEmpty());
    }

    @Test
    void findByDni_delegatesAndMaps() {
        ClienteEntity entity = new ClienteEntity();
        entity.setDni("11111111A");
        Cliente domain = new Cliente();
        domain.setDni("11111111A");

        when(clienteJpaRepository.findByDni("11111111A")).thenReturn(Optional.of(entity));
        when(clienteEntityMapper.toDomain(entity)).thenReturn(domain);

        Optional<Cliente> res = adapter.findByDni("11111111A");

        assertTrue(res.isPresent());
        assertEquals("11111111A", res.get().getDni());
    }

    @Test
    void findByTotalGreaterThan_invalid_returnsEmptyList() {
        assertTrue(adapter.findByTotalGreaterThan(-1.0f).isEmpty());
        assertTrue(adapter.findByTotalGreaterThan(null).isEmpty());
    }
}
