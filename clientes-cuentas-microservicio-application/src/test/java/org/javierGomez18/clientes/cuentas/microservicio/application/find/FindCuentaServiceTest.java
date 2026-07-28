package org.javierGomez18.clientes.cuentas.microservicio.application.find;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cliente.ClienteNotFoundException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.ClientesQueryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class FindCuentaServiceTest {

  @Mock private ClientesQueryRepository clientesRepository;

  @InjectMocks private FindCuentaService service;

  @Test
  void findClienteByDni_whenExists_returnsCliente() {
    var cliente = new Cliente();
    cliente.setDni("11111111A");
    when(clientesRepository.findByDni("11111111A")).thenReturn(Optional.of(cliente));

    var result = service.findClienteByDni("11111111A");

    assertEquals(cliente, result);
  }

  @Test
  void findClienteByDni_whenNotFound_throws() {
    when(clientesRepository.findByDni("x")).thenReturn(Optional.empty());
    assertThrows(ClienteNotFoundException.class, () -> service.findClienteByDni("x"));
  }

  @Test
  void findClientesAdultos_whenEmpty_throws() {
    when(clientesRepository.findAdultos()).thenReturn(List.of());
    assertThrows(ClienteNotFoundException.class, () -> service.findClientesAdultos());
  }

  @Test
  void findClientesByTotalGreaterThan_whenEmpty_throws() {
    when(clientesRepository.findByTotalGreaterThan(100.0f)).thenReturn(List.of());
    assertThrows(
        ClienteNotFoundException.class, () -> service.findClientesByTotalGreaterThan(100.0f));
  }

  @Test
  void findAllClientes_returnsList() {
    var c1 = new Cliente();
    when(clientesRepository.findAll()).thenReturn(List.of(c1));

    var result = service.findAllClientes();

    assertEquals(1, result.size());
  }
}
