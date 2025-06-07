package com.microservice.Cliente.controller;

import com.microservice.Cliente.model.Cliente;
import com.microservice.Cliente.service.ClienteService;
import com.microservice.Cliente.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;
import java.net.URI;
import java.time.LocalDateTime;



@RestController
@RequestMapping("/api/v1/Clientes")
public class ClienteController {

    @Autowired
    private ClienteService ClienteService;

    //localhost:8090/api/v1/Clientes/listar
    @GetMapping("/listar")
    public List<Cliente> getAllClientes(){
        return ClienteService.findAll();
    }
    //localhost:8090/api/v1/Cliente/1
    @GetMapping("/{id_Cliente}")
    public ResponseEntity<?> getPatientById(@PathVariable Integer id_Cliente){

        Optional<Cliente> Cliente = ClienteService.getClienteById(id_Cliente);

        if(Cliente.isPresent()){
            //Retorna la respuesta exitosa con cabeceras personalizadas

            ClienteDTO dto = new ClienteDTO();
            dto.setId_Cliente(Cliente.get().getId_Cliente());
            dto.setRut(Cliente.get().getRut());
            dto.setNombres(Cliente.get().getNombres());
            dto.setApellidos(Cliente.get().getApellidos());
            dto.setFechaNacimiento(Cliente.get().getFechaNacimiento());
            dto.setCorreo(Cliente.get().getCorreo());

            return ResponseEntity.ok()
                        .header("mi-encabezado","valor")
                        .body(Cliente.get());
        }
        else{
            //Respuesta de error con cuerpo personalizado
            Map<String,String> errorBody = new HashMap<>();
            errorBody.put("message","No se encontró el Cliente con ese ID: " + id_Cliente);
            errorBody.put("status","404");
            errorBody.put("timestamp",LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorBody);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ClienteDTO ClienteDTO){
        try{
            Cliente Cliente = new Cliente();
            Cliente.setRut(ClienteDTO.getRut());
            Cliente.setNombres(ClienteDTO.getNombres());
            Cliente.setApellidos(ClienteDTO.getApellidos());
            Cliente.setFechaNacimiento(ClienteDTO.getFechaNacimiento());
            Cliente.setCorreo(ClienteDTO.getCorreo());
        
            Cliente ClienteGuardado = ClienteService.save(Cliente);
        
            // Crear el DTO de respuesta
            ClienteDTO responseDTO = new ClienteDTO();
            responseDTO.setId_Cliente(ClienteGuardado.getId_Cliente());
            responseDTO.setRut(ClienteGuardado.getRut());
            responseDTO.setNombres(ClienteGuardado.getNombres());
            responseDTO.setApellidos(ClienteGuardado.getApellidos());
            responseDTO.setFechaNacimiento(ClienteGuardado.getFechaNacimiento());
            responseDTO.setCorreo(ClienteGuardado.getCorreo());
        
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(ClienteGuardado.getId_Cliente())
                    .toUri();

            return ResponseEntity.created(location).body(responseDTO);

            
        } catch(DataIntegrityViolationException e){
            //Ejemplo: Error si hay un campo único duplicado (ej: email repetido)
            Map<String,String> error = new HashMap<>();
            error.put("message","El email ya está registrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);//Error 409
        }
    }

    // POST localhost:8090/api/v1/pacientes/1
    @PutMapping("/{id_Cliente}")
    public ResponseEntity<ClienteDTO> update(@PathVariable int id_Cliente,@RequestBody ClienteDTO ClienteDTO){
        try{

            Paciente Cliente = new Cliente();
            Cliente.setId_paciente(id_Cliente);
            Cliente.setRut(ClienteDTO.getRut());
            Cliente.setNombres(ClienteDTO.getNombres());
            Cliente.setApellidos(ClienteDTO.getApellidos());
            Cliente.setFechaNacimiento(ClienteDTO.getFechaNacimiento());
            Cliente.setCorreo(ClienteDTO.getCorreo());

            Cliente ClienteActualizado = ClienteService.save(Cliente);
        
            ClienteDTO responseDTO = new ClienteDTO();
            responseDTO.setId_Cliente(ClienteActualizado.getId_Cliente());
            responseDTO.setRut(ClienteActualizado.getRut());
            responseDTO.setNombres(ClienteActualizado.getNombres());
            responseDTO.setApellidos(ClienteActualizado.getApellidos());
            responseDTO.setFechaNacimiento(ClienteActualizado.getFechaNacimiento());
            responseDTO.setCorreo(ClienteActualizado.getCorreo());
            
            return ResponseEntity.ok(responseDTO);

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }
    
    //localhost:8090/api/v1/Clientes/1
    @DeleteMapping("/{id_Cliente}")
    public ResponseEntity<?> eliminar(@PathVariable int id_Cliente){
        try{

            ClienteServiceService.delete(id_Cliente);
            return ResponseEntity.noContent().build();//Operacion exitosa pero no hay contenido para devolver.

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }
}