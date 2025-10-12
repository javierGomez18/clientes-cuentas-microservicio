package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.adapter;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javierGomez18.clientes.cuentas.microservicio.domain.excepciones.ClienteCuentasNotFoundException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.domain.repository.CuentaBancariaCommandRepository;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.DTO.Entities.ClienteEntity;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.DTO.Entities.CuentaBancariaEntity;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mappers.ToClienteEntityMapper;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mappers.ToCuentaBancariaEntityMapper;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.repository.ClienteJpaRepository;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.repository.CuentaBancariaJpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@AllArgsConstructor
public class CuentaBancariaRepositoryAdapter implements CuentaBancariaCommandRepository {

    private final CuentaBancariaJpaRepository cuentaBancariaJpaRepository;
    private final ClienteJpaRepository clienteJpaRepository;
    private final ToClienteEntityMapper toClienteEntityMapper;
    private final ToCuentaBancariaEntityMapper toCuentaBancariaEntityMapper;

    @Override
    @Transactional
    public void addCuenta(Cliente c) {

        log.info("Iniciando creacion cuenta para cliente: "+ c.getDni());

        ClienteEntity cliente = clienteJpaRepository.findByDni(c.getDni())
                .orElseGet(() -> {
                    log.info("No existe ningun Cliente con DNI: "+ c.getDni());
                    log.info("Insertando nuevo Cliente con DNI: "+ c.getDni());
                    return clienteJpaRepository.save(
                                toClienteEntityMapper.toClienteEntity(c)
                        );
                    }
                );
        log.info("Insertando cuenta para cliente: "+ c.getDni());
        CuentaBancariaEntity cuenta = toCuentaBancariaEntityMapper.toCuentaEntity(
                c.getCuentas().getFirst()
        );
        cuenta.setCliente(cliente);

        cuentaBancariaJpaRepository.save(cuenta);
        log.info("Cuenta creada correctamente");
    }

    @Override
    @Transactional
    public void updateCuenta(CuentaBancaria c) {
        log.info("Actualizando saldo de la cuenta: "+ c.getId());
        CuentaBancariaEntity cuenta = cuentaBancariaJpaRepository.findById(c.getId())
                .orElseThrow(()->new ClienteCuentasNotFoundException(c.getId()));
        cuenta.setTotal(c.getTotal());
    }
}
