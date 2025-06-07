package com.microservice.Cliente.service;

import com.microservice.Cliente.model.Cliente;
import com.microservice.Cliente.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ClienteService {

    @Autowired
    private ClienteRepository ClienteRepository;

    public List<Cliente> findAll(){
        return ClienteRepository.findAll();
    }

    public Optional<Cliente> getPatientById(int id_Cliente){
        return ClienteRepository.findById(id_Cliente);
    }

    public Cliente getPatientById2(int id){
        return ClienteRepository.findById(id).get();
    }

    //La funcion save sirve tanto para guardar como para editar un registro
    public Cliente save(Cliente Cliente){
        return ClienteRepository.save(Cliente);
    }

    public void delete(int id_Cliente){
        ClienteRepository.deleteById(id_Cliente);
    }

}