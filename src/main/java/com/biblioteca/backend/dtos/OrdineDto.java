package com.biblioteca.backend.dtos;

import java.time.LocalDate;

import com.biblioteca.backend.entities.Ordine;

import lombok.Data;

@Data
public class OrdineDto {
    private Long id;
    private Long userId;
    private String username; 
    private Long libroId;
    private String libroTitolo;
    private LocalDate dataOrdine;
    private LocalDate dataRestituzione;
    private boolean attivo;

    public OrdineDto(Ordine ordine) {
        this.id = ordine.getId();
        this.userId = ordine.getUser().getId();
        this.username = ordine.getUser().getUsername();
        this.libroId = ordine.getLibro().getId();
        this.libroTitolo = ordine.getLibro().getTitolo();
        this.dataOrdine = ordine.getDataOrdine();
        this.dataRestituzione = ordine.getDataRestituzione();
        this.attivo = ordine.isAttivo();
    }
}


