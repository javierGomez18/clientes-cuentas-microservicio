package org.javierGomez18.clientes.cuentas.microservicio.application.create;

import org.javierGomez18.clientes.cuentas.microservicio.domain.model.Cliente;
import org.javierGomez18.clientes.cuentas.microservicio.domain.model.CuentaBancaria;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.in.CreateCuentaUseCase;
import org.javierGomez18.clientes.cuentas.microservicio.domain.port.out.CuentaBancariaCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Slf4j
@RequiredArgsConstructor
public class CreateCuentaService implements CreateCuentaUseCase {

    private final CuentaBancariaCommandRepository cuentaBancariaRepository;

    @Override
    public void createCuenta(CreateCuentaCommand command) {
        log.info("Creando nueva cuenta para cliente: {}", command.dniCliente());
        
        Cliente cliente = new Cliente();
        cliente.setDni(command.dniCliente());
        cliente.setCuentas(new ArrayList<>());
        
        CuentaBancaria nuevaCuenta = new CuentaBancaria();
        nuevaCuenta.setDniCliente(command.dniCliente());
        nuevaCuenta.setTipoCuenta(command.tipoCuenta());
        nuevaCuenta.setTotal(command.saldoInicial());
        
        cliente.getCuentas().add(nuevaCuenta);
        
        cuentaBancariaRepository.addCuenta(cliente, nuevaCuenta);
        log.info("Cuenta creada exitosamente para cliente: {}", command.dniCliente());
    }
}

