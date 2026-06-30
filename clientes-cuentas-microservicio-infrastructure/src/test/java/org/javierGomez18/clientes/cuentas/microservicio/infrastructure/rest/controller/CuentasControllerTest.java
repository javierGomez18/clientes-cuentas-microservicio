package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.rest.controller;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class CuentasControllerTest {

    /*private final MockMvc mockMvc;

    @Test
    void postAddCuenta_addNewCuentaToExistingCliente_resultOK() throws Exception {

        String jsonMock = getJsonMockExistingCliente();

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMock))
                .andExpect(status().isCreated());
    }

    private static String getJsonMockExistingCliente(){
        return """
            {
              "dniCliente": "11111111A",
              "tipoCuenta": "PREMIUM",
              "total": 50000.0
            }
            """;
    }

    @Test
    void postAddCuenta_addNewCuentaToNotExistingCliente_resultOK() throws Exception {
        String jsonMock = getJsonMockNotExistingCliente();

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMock))
                .andExpect(status().isCreated());
    }

    private static String getJsonMockNotExistingCliente(){
        return """
            {
              "dniCliente": "70260404G",
              "tipoCuenta": "NORMAL",
              "total": 50000.0
            }
            """;
    }

    @Test
    void postAddCuenta_addNewCuentaWrongTipoCuenta_resultBadRequest() throws Exception {
        String jsonMock = getJsonMockWrongTipoCuenta();

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMock))
                .andExpect(status().isBadRequest());
    }

    private String getJsonMockWrongTipoCuenta(){
        return """
            {
              "dniCliente": "70260404G",
              "tipoCuenta": "CORRIENTE",
              "total": 50000.0
            }
            """;
    }

    @Test
    void putUpdateTotalCuenta_updateTotalCuenta_resultOK() throws Exception {

        int idCuentaMock = 4;
        String jsonMock = getJsonMockPutCuenta();

        mockMvc.perform(put("/cuentas/"+idCuentaMock)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMock))
                .andExpect(status().isOk());
    }

    @Test
    void putUpdateTotalCuenta_updateTotalCuenta_resultNotFound() throws Exception {

        int idCuentaMock = 14;
        String jsonMock = getJsonMockPutCuenta();

        mockMvc.perform(put("/cuentas/"+idCuentaMock)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonMock))
                .andExpect(status().isNotFound());
    }

    private static String getJsonMockPutCuenta() throws Exception {
        return """
            {
              "total": 50000.0
            }
            """;
    }*/
}
