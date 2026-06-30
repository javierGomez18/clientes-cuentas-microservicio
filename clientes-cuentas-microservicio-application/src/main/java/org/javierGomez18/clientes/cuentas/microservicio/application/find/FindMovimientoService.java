package org.javierGomez18.clientes.cuentas.microservicio.application.find;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageRequest;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.PageResult;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindMovimientoUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.MovimientoQueryRepository;

@Slf4j
@RequiredArgsConstructor
public class FindMovimientoService implements FindMovimientoUseCase {

  private final MovimientoQueryRepository movimientoRepository;
  private static String DEFAULT_DESCEND_SORTING = "desc";

  @Override
  public PageResult<Movimiento> findByCuentaId(Long cuentaId, int page, int size, String sort) {
    log.info("Obteniendo movimientos de la cuenta: {}", cuentaId);
    return movimientoRepository.findByCuentaId(
        cuentaId,
        PageRequest.builder()
            .page(page)
            .size(size)
            .descending(sort.equals(DEFAULT_DESCEND_SORTING))
            .build());
  }
}
