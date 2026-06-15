package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

import lombok.Builder;

import java.util.List;

@Builder
public record PageResult<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) {}
