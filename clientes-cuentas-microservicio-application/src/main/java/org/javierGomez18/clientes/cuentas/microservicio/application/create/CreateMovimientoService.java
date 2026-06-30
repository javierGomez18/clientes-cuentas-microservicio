package org.javierGomez18.clientes.cuentas.microservicio.application.create;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cuenta.CuentaNotFoundException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.movimiento.MovimientoUnprocessableEntityException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Movimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.TipoMovimiento;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.CreateMovimientoUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.CuentaBancariaCommandRepository;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.CuentaBancariaQueryRepository;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.MovimientoCommandRepository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional
public class CreateMovimientoService implements CreateMovimientoUseCase {

  private final CuentaBancariaCommandRepository cuentaBancariaCommandRepository;
  private final CuentaBancariaQueryRepository cuentaBancariaQueryRepository;
  private final MovimientoCommandRepository movimientoCommandRepository;

  @Override
  public Movimiento createMovimiento(CreateMovimientoCommand command) {
    TipoMovimiento tipoMovimiento = TipoMovimiento.fromString(command.tipoMovimiento());
    TipoMovimiento tipoOrigen =
        tipoMovimiento == TipoMovimiento.TRANSFERENCIA ? TipoMovimiento.RETIRADA : tipoMovimiento;
    CuentaBancaria cuentaOrigen =
        cuentaBancariaQueryRepository
            .findCuenta(command.cuentaIdOrigen())
            .orElseThrow(() -> new CuentaNotFoundException(command.cuentaIdOrigen()));
    BigDecimal importe = BigDecimal.valueOf(command.cantidad());

    /*Optional.of(cuentaOrigen)
    .filter(CuentaBancaria::isActiva)
    .orElseThrow(() -> new CuentaBloqueadaException(id));*/

    if (tipoOrigen == TipoMovimiento.RETIRADA) {
      Optional.of(cuentaOrigen)
          .filter(c -> c.getTotal() >= command.cantidad())
          .orElseThrow(
              () -> new MovimientoUnprocessableEntityException(command.cuentaIdOrigen(), importe));
    }

    Movimiento movimientoOrigen =
        movimientoCommandRepository
            .addMovimiento(
                Movimiento.builder()
                    .fechaOperacion(LocalDate.now())
                    .tipo(tipoOrigen)
                    .importe(importe)
                    .cuentaId(command.cuentaIdOrigen())
                    .descripcion(command.descripcion())
                    .build())
            .orElseThrow(
                () -> new IllegalStateException("No se pudo persistir el movimiento de origen"));

    cuentaOrigen.aplicarMovimiento(tipoOrigen, command.cantidad().floatValue());
    cuentaBancariaCommandRepository.updateCuenta(cuentaOrigen);

    if (tipoMovimiento == TipoMovimiento.TRANSFERENCIA) {
      CuentaBancaria cuentaDestino =
          cuentaBancariaQueryRepository
              .findCuenta(command.cuentaIdDestino())
              .orElseThrow(() -> new CuentaNotFoundException(command.cuentaIdDestino()));
      cuentaDestino.aplicarMovimiento(TipoMovimiento.INGRESO, command.cantidad().floatValue());
      movimientoCommandRepository
          .addMovimiento(
              Movimiento.builder()
                  .fechaOperacion(LocalDate.now())
                  .tipo(TipoMovimiento.INGRESO)
                  .importe(importe)
                  .cuentaId(command.cuentaIdDestino())
                  .descripcion("Transferencia recibida: " + command.descripcion())
                  .build())
          .orElseThrow(
              () -> new IllegalStateException("No se pudo persistir el movimiento de destino"));
      cuentaBancariaCommandRepository.updateCuenta(cuentaDestino);
    }
    return movimientoOrigen;
  }
}
