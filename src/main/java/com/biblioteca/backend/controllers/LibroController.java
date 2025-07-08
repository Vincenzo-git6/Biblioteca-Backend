package com.biblioteca.backend.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.backend.dtos.LibroDto;
import com.biblioteca.backend.services.LibroService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/libri")
@RequiredArgsConstructor
public class LibroController 
{
    private final LibroService libroService;


    @GetMapping("/tutti")
    public List<LibroDto> tuttiLibri()
    {
        return libroService.getAllLibri();
    }
    
    /*
     *  TODO: attenzione, limitare l'utilizzo di path variables
     */
    @GetMapping("/{id}")
    public LibroDto singleLibro(@PathVariable Long id)
    {
        return libroService.getOneLibro(id);
    }

    @GetMapping("/paginati")
    public Page<LibroDto> libriPaginati(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size)
    {
        return libroService.getLibriPaginati(page, size);
    }

    @GetMapping("/sortati")
    public Page<LibroDto> libriPaginatiESortati(
            @RequestParam(defaultValue = "0") int page, 
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "autore") String sortBy, 
            @RequestParam(defaultValue = "asc") String direzione
            )
    {
        return libroService.getLibriPaginatiESortati(page, size, sortBy, direzione);
    }


    @GetMapping("/criteria")
    public List<LibroDto> getLibriByAutoreETitolo(@RequestParam String autore, @RequestParam String titolo)
    {
        return libroService.getLibriByAutoreETitolo(autore, titolo);
    }

}
