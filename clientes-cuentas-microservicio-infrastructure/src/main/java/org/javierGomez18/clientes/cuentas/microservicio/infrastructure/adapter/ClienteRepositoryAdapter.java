package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.adapter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.ClientesQueryRepository;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.ClienteEntityMapper;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.repository.ClienteJpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Adaptador: Implementa ClientesQueryRepository (Puerto OUT) Convierte llamadas del dominio a
 * queries JPA
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClientesQueryRepository {

  private final ClienteJpaRepository clienteJpaRepository;
  private final ClienteEntityMapper clienteEntityMapper;

  @Override
  public List<Cliente> findAll() {
    log.debug("Obteniendo todos los clientes desde BD");
    var clientes = clienteJpaRepository.findAll();
    log.info("Se encontraron {} clientes en BD", clientes.size());
    return clienteEntityMapper.toDomainList(clientes);
  }

  @Override
  public Optional<Cliente> findByDni(String dni) {
    log.debug("Buscando cliente por DNI: {}", dni);
    if (dni == null || dni.isBlank()) {
      log.warn("DNI inválido proporcionado: null o vacío");
      return Optional.empty();
    }
    var cliente = clienteJpaRepository.findByDni(dni);
    log.info(
        "Búsqueda de cliente DNI: {} - {}",
        dni,
        cliente.isPresent() ? "ENCONTRADO" : "NO ENCONTRADO");
    return cliente.map(clienteEntityMapper::toDomain);
  }

  @Override
  public List<Cliente> findAdultos() {
    log.debug("Buscando clientes adultos (>= 18 años)");
    LocalDate fechaLimite = LocalDate.now().minusYears(18);
    var adultos = clienteJpaRepository.findByFechaNacimientoBefore(fechaLimite);
    log.info("Se encontraron {} clientes adultos", adultos.size());
    return clienteEntityMapper.toDomainList(adultos);
  }

  @Override
  public List<Cliente> findByTotalGreaterThan(Float total) {
    log.debug("Buscando clientes con total mayor a: {}", total);
    if (total == null || total < 0) {
      log.warn("Total inválido: {}", total);
      return List.of();
    }
    var clientes = clienteJpaRepository.findClientesByTotalCuentas(BigDecimal.valueOf(total));
    log.info("Se encontraron {} clientes con total > {}", clientes.size(), total);
    return clienteEntityMapper.toDomainList(clientes);
  }
}
