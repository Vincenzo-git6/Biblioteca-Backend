package com.biblioteca.backend.dtos;

import com.biblioteca.backend.entities.Libro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroDto 
{
        private Long id;
        private String titolo;
        private String autore;
        private boolean disponibile;


        public LibroDto(Libro libro)
        {
            this.id = libro.getId();
            this.titolo = libro.getTitolo();
            this.autore = libro.getAutore();
            this.disponibile = libro.isDisponibile();
        }
}
