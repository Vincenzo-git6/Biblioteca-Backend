package com.biblioteca.backend.services;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.backend.dtos.LibroDto;
import com.biblioteca.backend.entities.Libro;
import com.biblioteca.backend.exceptions.LibroNonEsistenteException;
import com.biblioteca.backend.interfaces.ILibro;
import com.biblioteca.backend.repositories.LibroRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LibroService implements ILibro
{
    
    private final LibroRepository libroRepository;
    
    //ottieni tutti i libri nel db
    public List<LibroDto> getAllLibri()
    {
        return libroRepository.findAll()
                .stream()
                .map(LibroDto::new) //:) mi piace
                .toList();
    }

    //prendi un libro partendo da un id
    public LibroDto getOneLibro(Long id)
    {
        return libroRepository.findById(id)
                .map(LibroDto::new)
                .orElseThrow(() -> new LibroNonEsistenteException("Prodotto non trovato"));
    }

    //crea un nuovo libro
    //solo per utente con ruolo admin
    @Transactional
    public Libro nuovoLibro(Libro libro)
    {
        return libroRepository.save(libro);
    }

    //modiifca un libro esistente
    //solo admin
    @Transactional
    public Libro modificaLibro(Long id, Libro libroModificato)
    {
        Libro libro = libroRepository.findById(id)
                .orElseThrow(() -> new LibroNonEsistenteException("Nessun libro trovato con l'id: "+id));
        
        libro.setAutore(libroModificato.getAutore());
        libro.setDisponibile(libroModificato.isDisponibile());
        libro.setTitolo(libroModificato.getTitolo());

        return libroRepository.save(libro);
    }

    //elimina un libro esistente dando come parametro l'id di quel libro
    //solo per utente con ruolo admin
    @Transactional
    public void eliminaLibro(Long id)
    {
        if (!libroRepository.existsById(id)) 
        {
            throw new IllegalArgumentException(String.format("Libro con id %s non trovato", id));   
        }

        libroRepository.deleteById(id);
    }


    //Andiamo a paginare i libri, ovvero diciamo che in un pagina verranno visualizzati tot libri (deciso poi nel controller)
    public Page<LibroDto> getLibriPaginati(int page, int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<Libro> libriPage = libroRepository.findAll(pageable);

        return libriPage.map(LibroDto::new);
    }


    //Andiamo a paginare e sortare per qualcosa che poi possiamo decidere direttamente nel controller con @RequestParam
    public Page<LibroDto> getLibriPaginatiESortati(int page, int size, String sortBy, String direzione)
    {
        Sort sort = direzione.equalsIgnoreCase("desc") ? 
                    Sort.by(sortBy).descending() : 
                    Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Libro> libriPage = libroRepository.findAll(pageable);

        return libriPage.map(LibroDto::new);
    }

    public List<LibroDto> getLibriByAutoreETitolo(String autore, String titolo)
    {
        List<Libro> libro = libroRepository.findLibriByAutoreETitolo(autore, titolo);

        if (libro.isEmpty()) 
        {
            throw new LibroNonEsistenteException("Non ci sono libri con titolo: '"+titolo+"' dell'autore: "+autore);
        }
        
        return libro.stream().map(LibroDto::new).toList();
    }

    
    

}
