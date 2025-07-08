package com.biblioteca.backend.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.backend.entities.Libro;
import com.biblioteca.backend.services.LibroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/libro")
@RequiredArgsConstructor
public class AdminLibroController 
{
    private final LibroService libroService;

    @PostMapping("/inserisci")
    public Libro aggiungiLibro(@RequestBody Libro libro)
    {
        return libroService.nuovoLibro(libro);
    }

    @PutMapping("/modifica/{id}")
    public ResponseEntity<?> modificaLibro(@PathVariable Long id, @RequestBody Libro libroModificato)
    {
        try {
            Libro libroAggiornato = libroService.modificaLibro(id, libroModificato);
            return ResponseEntity.ok(libroAggiornato);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/elimina/{id}")
    public ResponseEntity<?> eliminaLibro(@PathVariable Long id)
    {
        libroService.eliminaLibro(id);
        return ResponseEntity.ok().body(Map.of("message","Libro eliminato"));
    }
    
}
