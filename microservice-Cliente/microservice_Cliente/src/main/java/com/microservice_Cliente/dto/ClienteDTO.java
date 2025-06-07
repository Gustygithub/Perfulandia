package com.microservice.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;


import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    
    private int id_cliente;
    private String rut;
    private String nombres;
    private String apellidos;
    private Date fechaNacimiento;
    private String correo;

}