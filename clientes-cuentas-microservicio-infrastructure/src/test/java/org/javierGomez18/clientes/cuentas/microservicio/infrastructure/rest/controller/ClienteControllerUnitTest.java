package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.ClienteResponseMapper;
import org.javierGomez18.clientes.cuentas.microservicio.web.dto.ClienteRS;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ClienteControllerUnitTest {

  @Mock FindCuentaUseCase findCuentaUseCase;

  @Mock ClienteResponseMapper clienteResponseMapper;

  @InjectMocks ClienteController controller;

  @Test
  void findAllClientes_returnsMappedList() {
    Cliente c = new Cliente();
    c.setDni("11111111A");
    when(findCuentaUseCase.findAllClientes()).thenReturn(List.of(c));

    ClienteRS rs = new ClienteRS();
    rs.setDniCliente("11111111A");
    when(clienteResponseMapper.toResponseList(any())).thenReturn(List.of(rs));

    ResponseEntity<List<ClienteRS>> resp = controller.findAllClientes();
    assertEquals(200, resp.getStatusCodeValue());
    assertEquals(1, resp.getBody().size());
    assertEquals("11111111A", resp.getBody().get(0).getDniCliente());
  }

  @Test
  void findClienteByDni_returnsMapped() {
    Cliente c = new Cliente();
    c.setDni("22222222B");
    when(findCuentaUseCase.findClienteByDni("22222222B")).thenReturn(c);

    ClienteRS rs = new ClienteRS();
    rs.setDniCliente("22222222B");
    when(clienteResponseMapper.toResponse(c)).thenReturn(rs);

    var resp = controller.findClienteByDni("22222222B");
    assertEquals(200, resp.getStatusCodeValue());
    assertEquals("22222222B", resp.getBody().getDniCliente());
  }

  @Test
  void findClientesAdultos_and_findByTotal_work() {
    Cliente c = new Cliente();
    c.setDni("33333333C");
    when(findCuentaUseCase.findClientesAdultos()).thenReturn(List.of(c));
    when(clienteResponseMapper.toResponseList(any())).thenReturn(List.of(new ClienteRS()));

    var resp = controller.findClientesAdultos();
    assertEquals(200, resp.getStatusCodeValue());

    when(findCuentaUseCase.findClientesByTotalGreaterThan(100.0f)).thenReturn(List.of(c));
    var resp2 = controller.findClientesByTotalGreaterThan(100.0f);
    assertEquals(200, resp2.getStatusCodeValue());
  }
}
