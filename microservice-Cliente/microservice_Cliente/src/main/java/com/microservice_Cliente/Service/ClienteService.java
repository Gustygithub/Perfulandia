package com.microservice.cliente.service;

import com.microservice.cliente.model.Cliente;
import com.microservice.cliente.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Cliente> findAll(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getPatientById(int id_cliente){
        return clienteRepository.findById(id_Cliente);
    }

    public Cliente getPatientById2(int id){
        return clienteRepository.findById(id).get();
    }

    //La funcion save sirve tanto para guardar como para editar un registro
    public Cliente save(Cliente cliente){
        return clienteRepository.save(Cliente);
    }

    public void delete(int id_cliente){
        clienteRepository.deleteById(id_cliente);
    }

}