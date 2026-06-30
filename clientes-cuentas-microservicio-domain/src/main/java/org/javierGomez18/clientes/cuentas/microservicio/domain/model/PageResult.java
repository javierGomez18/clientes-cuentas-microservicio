package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

import java.util.List;
import lombok.Builder;

@Builder
public record PageResult<T>(
    List<T> content, int page, int size, long totalElements, int totalPages) {}
