package com.biblioteca.backend.entities;

import com.biblioteca.backend.enums.TipoComputer;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
public class Computer extends ServiziAstratti
{

    protected Computer(ServiziAstrattiBuilder<?, ?> b) {
        super(b);
    }
    
    private TipoComputer tipo;
}
