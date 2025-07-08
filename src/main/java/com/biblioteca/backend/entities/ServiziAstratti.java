package com.biblioteca.backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Data
@SuperBuilder
public abstract class ServiziAstratti 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String serialNumber;

    @Column
    private String nome;


    public boolean pippo2() {
        return true;
    }
    
}
