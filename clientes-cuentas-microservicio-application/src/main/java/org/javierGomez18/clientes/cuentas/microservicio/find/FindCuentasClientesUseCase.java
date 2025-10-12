package org.javierGomez18.clientes.cuentas.microservicio.find;

import lombok.RequiredArgsConstructor;
import org.javierGomez18.clientes.cuentas.microservicio.domain.excepciones.ClienteCuentasNotFoundException;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.repository.ClientesQueryRepository;

import java.util.List;

@RequiredArgsConstructor
public class FindCuentasClientesUseCase {

    private final ClientesQueryRepository clientesRepository;

    public List<Cliente> getClientes(){
        return clientesRepository.getClientes();
    }

    public List<Cliente> getClientesAdultos(){
        List<Cliente> result = clientesRepository.getClientesAdultos();

        if(result.isEmpty()){
            throw new ClienteCuentasNotFoundException();
        }

        return result;
    }

    public List<Cliente> getClientesByTotal(Float total){
        List<Cliente> result = clientesRepository.getClientesByTotal(total);

        if(result.isEmpty()){
            throw new ClienteCuentasNotFoundException(total);
        }
        return result;
    }

    public Cliente getClienteByDni(String dni){
        return clientesRepository.getClienteByDni(dni);
    }
}
