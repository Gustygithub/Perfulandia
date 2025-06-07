package com.microservice.Cliente.repository;

import com.microservice.Cliente.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Paciente,Integer>{
    //Metodos heredados aqui
}