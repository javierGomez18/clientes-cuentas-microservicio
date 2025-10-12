package org.javierGomez18.clientes.cuentas.microservicio.infrastructure.adapter;

import lombok.RequiredArgsConstructor;
import org.javierGomez18.clientes.cuentas.microservicio.domain.excepciones.ClienteCuentasNotFoundException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.repository.ClientesQueryRepository;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.mappers.ToClienteDomainMapper;
import org.javierGomez18.clientes.cuentas.microservicio.infrastructure.repository.ClienteJpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClienteRepositoryAdapter implements ClientesQueryRepository {

    private final ClienteJpaRepository clienteJpaRepository;
    private final ToClienteDomainMapper clientesmapper;

    @Override
    public List<Cliente> getClientes() {
        return clientesmapper.toClienteDomainLst(clienteJpaRepository.findAll());
    }

    @Override
    public List<Cliente> getClientesAdultos() {
        return clientesmapper.toClienteDomainLst(
                clienteJpaRepository.findClienteEntitiesByFechaNacimientoIsLessThanEqual(
                        LocalDate.now().minusYears(18)
                )
        );
    }

    @Override
    public List<Cliente> getClientesByTotal(Float total) {
        return clientesmapper.toClienteDomainLst(clienteJpaRepository.findClientesByTotalCuentas(BigDecimal.valueOf(total)));
    }

    @Override
    public Cliente getClienteByDni(String dni) {
        return clienteJpaRepository.findByDni(dni).map(clientesmapper::toClienteDomain).orElseThrow(() -> new ClienteCuentasNotFoundException(dni));
    }
}
