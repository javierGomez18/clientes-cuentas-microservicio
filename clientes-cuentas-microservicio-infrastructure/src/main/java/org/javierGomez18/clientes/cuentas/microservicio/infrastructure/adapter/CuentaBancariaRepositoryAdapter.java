package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.adapter;

import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.cliente.ClienteNotFoundException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.CuentaBancariaCommandRepository;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.CuentaBancariaQueryRepository;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.ClienteEntityMapper;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mapper.CuentaEntityMapper;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.ClienteEntity;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.entity.CuentaBancariaEntity;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.repository.ClienteJpaRepository;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.persistence.repository.CuentaBancariaJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class CuentaBancariaRepositoryAdapter implements CuentaBancariaCommandRepository, CuentaBancariaQueryRepository {

    private final CuentaBancariaJpaRepository cuentaBancariaJpaRepository;
    private final ClienteJpaRepository clienteJpaRepository;
    private final ClienteEntityMapper clienteEntityMapper;
    private final CuentaEntityMapper cuentaEntityMapper;

    @Override
    @Transactional
    public void addCuenta(Cliente cliente, CuentaBancaria cuenta) {
        log.debug("Iniciando creación de cuenta para cliente: {}", cliente.getDni());
        
        if (cliente == null || cliente.getDni() == null) {
            log.error("Cliente o DNI nulo al intentar crear cuenta");
            throw new IllegalArgumentException("Cliente y DNI no pueden ser nulos");
        }

        ClienteEntity clienteEntity = clienteJpaRepository.findByDni(cliente.getDni())
                .orElseGet(() -> {
                    log.info("Cliente no existe, creando nuevo cliente con DNI: {}", cliente.getDni());
                    return clienteJpaRepository.save(clienteEntityMapper.toEntity(cliente));
                });

        CuentaBancariaEntity cuentaEntity = cuentaEntityMapper.toEntity(cuenta);
        cuentaEntity.setCliente(clienteEntity);

        cuentaBancariaJpaRepository.save(cuentaEntity);
        log.info("Cuenta creada exitosamente para cliente: {} - Tipo: {}", cliente.getDni(), cuenta.getTipoCuenta());
    }

    @Override
    @Transactional
    public void updateCuenta(CuentaBancaria cuenta) {
        log.debug("Actualizando saldo de cuenta: {}", cuenta.getId());
        
        if (cuenta == null || cuenta.getId() == null) {
            log.error("Cuenta o ID de cuenta nulo");
            throw new IllegalArgumentException("Cuenta e ID no pueden ser nulos");
        }
        
        if (cuenta.getTotal() == null || cuenta.getTotal() < 0) {
            log.error("Saldo inválido: {}", cuenta.getTotal());
            throw new IllegalArgumentException("Saldo no puede ser negativo");
        }

        CuentaBancariaEntity cuentaEntity = cuentaBancariaJpaRepository.findById(cuenta.getId())
                .orElseThrow(() -> {
                    log.warn("Cuenta no encontrada con ID: {}", cuenta.getId());
                    return new ClienteNotFoundException(cuenta.getId());
                });

        Float saldoAnterior = cuentaEntity.getTotal();
        cuentaEntity.setTotal(cuenta.getTotal());
        cuentaBancariaJpaRepository.save(cuentaEntity);
        
        log.info("Cuenta actualizada - ID: {} - Saldo anterior: {} - Saldo nuevo: {}", 
                 cuenta.getId(), saldoAnterior, cuenta.getTotal());
    }

    @Override
    public Optional<CuentaBancaria> findCuenta(Long idCuenta) {
        log.debug("Buscando cuenta con id: {}", idCuenta);
        return cuentaBancariaJpaRepository.findById(idCuenta).map(cuentaEntityMapper::toDomain);
    }
}
