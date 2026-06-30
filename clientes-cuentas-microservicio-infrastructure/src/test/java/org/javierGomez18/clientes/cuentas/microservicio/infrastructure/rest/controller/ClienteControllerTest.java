package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import static org.hamcrest.Matchers.*;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ClienteControllerTest {

  /*private final MockMvc mockMvc;

  @Test
  void getCliente_returnFirstFiveClients_resultOK() throws Exception {
      mockMvc.perform(get("/clientes"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$.length()")
                      .value(5)
              );
  }

  @Test
  void getClientesAdultos_returnOnlyAdults_resultOK() throws Exception {
      mockMvc.perform(get("/clientes/mayores-de-edad"))
              .andExpect(status().isOk())
              .andExpect(jsonPath("$[*].fechaNacimiento")
                      .value(
                              everyItem(
                                      lessThanOrEqualTo(
                                              LocalDate.now().minusYears(18).toString()
                                      )
                              )
                      )
              );
  }

  @Test
  void getClienteById_returnCorrectCliente_resultOK() throws Exception {
      String dniMock = "11111111A";
      mockMvc.perform(get("/clientes/"+dniMock))
              .andExpect(status().isOk())
              .andExpect(
                      jsonPath("$.dniCliente")
                              .value(dniMock)
              )
              .andExpect(
                      jsonPath("$.cuentas.length()")
                              .value(2)
              );
  }

  @Test
  void getClienteById_returnCliente_resultNotFound() throws Exception {
      String dniMock = "70260404G";
      mockMvc.perform(get("/clientes/"+dniMock))
              .andExpect(status().isNotFound());
  }

  @Test
  void getClientesCantidadSuperior_returnAllCLientesGreaterThan_resultOK() throws Exception {
      float totalMock = 10000F;

      String response = mockMvc.perform(get("/clientes/con-cuenta-superior-a/"+totalMock))
              .andExpect(status().isOk())
              .andReturn()
              .getResponse()
              .getContentAsString();

      ObjectMapper mapper = new ObjectMapper();
      List<Map<String, Object>> clientes = mapper.readValue(response, new TypeReference<>() {});

      clientes.stream()
              .map(cliente -> Map.entry(
                      cliente.get("dniCliente"),
                              ((List<Map<String, Object>>) cliente.get("cuentas"))
                                      .stream()
                                      .mapToDouble(c ->
                                              Double.parseDouble(c.get("total").toString()
                                              )
                                      )
                                      .sum()
                      )
              )
              .forEach(entry -> {
                  assertTrue(
                          entry.getValue() > totalMock,
                          "El cliente " + entry.getKey() + " no supera el total mínimo"
                  );
              });

  }

  @Test
  void getClientesCantidadSuperior_returnAllCLientesGreaterThan_resultNoContent() throws Exception {
      float totalMock = 1000000F;

      mockMvc.perform(get("/clientes/con-cuenta-superior-a/"+totalMock))
              .andExpect(status().isNoContent());
  }*/
}
