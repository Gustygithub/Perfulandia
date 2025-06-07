package com.microservice.cliente.controller;

import com.microservice.cliente.model.cliente;
import com.microservice.cliente.service.clienteService;
import com.microservice.cliente.dto.clienteDTO;
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
@RequestMapping("/api/v1/plientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    //localhost:8090/api/v1/clientes/listar
    @GetMapping("/listar")
    public List<Cliente> getAllClientes(){
        return clienteService.findAll();
    }
    //localhost:8090/api/v1/cliente/1
    @GetMapping("/{id_Cliente}")
    public ResponseEntity<?> getClienteById(@PathVariable Integer id_cliente){

        Optional<Cliente> cliente = clienteService.getClienteById(id_cliente);

        if(cliente.isPresent()){
            //Retorna la respuesta exitosa con cabeceras personalizadas

            ClienteDTO dto = new Cliente();
            dto.setId_Cliente(clienteliente.get().getId_cliente());
            dto.setRut(clienteliente.get().getRut());
            dto.setNombres(clienteliente.get().getNombres());
            dto.setApellidos(cliente.get().getApellidos());
            dto.setFechaNacimiento(cliente.get().getFechaNacimiento());
            dto.setCorreo(cliente.get().getCorreo());

            return ResponseEntity.ok()
                        .header("mi-encabezado","valor")
                        .body(cliente.get());
        }
        else{
            //Respuesta de error con cuerpo personalizado
            Map<String,String> errorBody = new HashMap<>();
            errorBody.put("message","No se encontró el cliente con ese ID: " + id_cliente);
            errorBody.put("status","404");
            errorBody.put("timestamp",LocalDateTime.now().toString());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorBody);
        }
    }
    
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody cliente cliente){
        try{
            Cliente cliente = new Cliente();
            cliente.setRut(clientelienteDTO.getRut());
            cliente.setNombres(cliente.getNombres());
            cliente.setApellidos(cliente.getApellidos());
            cliente.setFechaNacimiento(cliente.getFechaNacimiento());
            cliente.setCorreo(cliente.getCorreo());
        
            Cliente clienteGuardado = clienteService.save(cliente);
        
            // Crear el DTO de respuesta
            ClienteDTO responseDTO = new cliente();
            responseDTO.setId_cliente(clienteGuardado.getId_cliente());
            responseDTO.setRut(clienteGuardado.getRut());
            responseDTO.setNombres(clienteGuardado.getNombres());
            responseDTO.setApellidos(clienteGuardado.getApellidos());
            responseDTO.setFechaNacimiento(clienteGuardado.getFechaNacimiento());
            responseDTO.setCorreo(clienteGuardado.getCorreo());
        
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(clienteGuardado.getId_cliente())
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
    @PutMapping("/{id_cliente}")
    public ResponseEntity<ClienteDTO> update(@PathVariable int id_cliente,@RequestBody ClienteDTO clienteDTO){
        try{

            Cliente cliente = new Cliente();
            cliente.setId_cliente(id_cliente);
            cliente.setRut(cliente.getRut());
            cliente.setNombres(cliente.getNombres());
            cliente.setApellidos(cliente.getApellidos());
            cliente.setFechaNacimiento(cliente.getFechaNacimiento());
            cliente.setCorreo(clienteDTO.getCorreo());

            Cliente clienteactualizado = clienteService.save(cliente);
        
            Cliente responseDTO = new Cliente();
            responseDTO.setId_cliente(clienteactualizado.getId_cliente());
            responseDTO.setRut(clienteactualizado.getRut());
            responseDTO.setNombres(clienteactualizado.getNombres());
            responseDTO.setApellidos(clienteactualizado.getApellidos());
            responseDTO.setFechaNacimiento(clienteactualizado.getFechaNacimiento());
            responseDTO.setCorreo(clienteactualizado.getCorreo());
            
            return ResponseEntity.ok(responseDTO);

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }
    
    //localhost:8090/api/v1/clientes/1
    @DeleteMapping("/{id_Cliente}")
    public ResponseEntity<?> eliminar(@PathVariable int id_Cliente){
        try{

            clienteService.delete(id_cliente);
            return ResponseEntity.noContent().build();//Operacion exitosa pero no hay contenido para devolver.

        }catch(Exception ex){
            return ResponseEntity.notFound().build();
        }
    }
}