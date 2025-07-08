package com.biblioteca.backend.entities;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
public class Condizionatore extends ServiziAstratti{

    protected Condizionatore(ServiziAstrattiBuilder<?, ?> b) {
        super(b);
       
    }
    
    private int potenza;

    private String produttore;
}
