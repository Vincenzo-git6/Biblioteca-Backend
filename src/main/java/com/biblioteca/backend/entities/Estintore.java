package com.biblioteca.backend.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
public class Estintore extends ServiziAstratti{

    protected Estintore(ServiziAstrattiBuilder<?, ?> b) {
        super(b); 
    }

    @Column(nullable = false)
    private Date dataCostruzione;

    @Column(nullable = false)
    private Date ultimaVerifica;
}
