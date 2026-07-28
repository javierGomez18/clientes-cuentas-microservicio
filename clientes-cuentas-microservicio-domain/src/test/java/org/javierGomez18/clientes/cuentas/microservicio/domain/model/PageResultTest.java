package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;

class PageResultTest {

  @Test
  void builder_and_accessors_work() {
    PageResult<String> pr =
        PageResult.<String>builder()
            .content(List.of("a", "b"))
            .page(0)
            .size(2)
            .totalElements(2)
            .totalPages(1)
            .build();
    assertEquals(2, pr.content().size());
    assertEquals(0, pr.page());
    assertEquals(2, pr.size());
  }
}
