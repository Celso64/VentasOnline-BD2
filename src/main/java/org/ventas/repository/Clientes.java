package org.ventas.repository;

import org.ventas.model.Cliente;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Clientes {

    private Set<Cliente> clientes;

    public Clientes() {
        this.clientes = new HashSet<>();
    }

    public Boolean agregarCliente(Cliente cliente){
        Objects.requireNonNull(cliente);
        return this.clientes.add(cliente);
    }

    public Optional<Cliente> buscarCliente(String dni){
        return this.clientes.stream().filter(x -> x.mismoDNI(dni)).findFirst();
    }

    public Optional<Cliente> buscarCliente(Long id){
        return this.clientes.stream().filter(x -> x.equals(new Cliente(id))).findFirst();
    }

    public Boolean quitarCliente (String dni){
        return this.clientes.removeIf(x -> x.mismoDNI(dni));
    }

    public String listarClientes(){
        StringBuffer sb = new StringBuffer(this.clientes.size() * 32);
        this.clientes.stream().forEach(x -> sb.append(x.toString()));
        return sb.toString();
    }
}
