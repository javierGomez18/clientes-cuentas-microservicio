package org.javierGomez18.clientes.cuentas.microservicio.domain.model;

import lombok.Builder;

@Builder
public record PageRequest(int page, int size, boolean descending) {}
