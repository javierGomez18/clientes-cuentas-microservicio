package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageRequestTest {

    @Test
    void builder_setsValues() {
        PageRequest pr = PageRequest.builder().page(2).size(5).descending(true).build();
        assertEquals(2, pr.page());
        assertEquals(5, pr.size());
        assertTrue(pr.descending());
    }
}
