package com.biblioteca.backend.dtos;

import lombok.Data;

@Data
public class OrdineRequest 
{
    //si useranno poi nel controller degli ordini per 
    //creare un nuovo ordine tramite il @RequestBody
   private Long userId;
   private Long libroId; 
}
