package org.javierGomez18.clientes.cuentas.microservicio.application.find;

import org.javierGomez18.clientes.cuentas.microservicio.domain.exception.ClienteNotFoundException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.FindCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.ClientesQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class FindCuentaService implements FindCuentaUseCase {

    private final ClientesQueryRepository clientesRepository;

    @Override
    public Cliente findClienteByDni(String dni) {
        log.info("Buscando cliente por DNI: {}", dni);
        return clientesRepository.findByDni(dni)
                .orElseThrow(() -> new ClienteNotFoundException(dni));
    }

    @Override
    public List<Cliente> findAllClientes() {
        log.info("Obteniendo todos los clientes");
        return clientesRepository.findAll();
    }

    @Override
    public List<Cliente> findClientesAdultos() {
        log.info("Obteniendo clientes adultos");
        List<Cliente> adultos = clientesRepository.findAdultos();
        if (adultos.isEmpty()) {
            throw new ClienteNotFoundException();
        }
        return adultos;
    }

    @Override
    public List<Cliente> findClientesByTotalGreaterThan(Float cantidad) {
        log.info("Obteniendo clientes con total mayor a: {}", cantidad);
        List<Cliente> clientes = clientesRepository.findByTotalGreaterThan(cantidad);
        if (clientes.isEmpty()) {
            throw new ClienteNotFoundException(cantidad);
        }
        return clientes;
    }
}

