package com.biblioteca.backend.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.biblioteca.backend.dtos.OrdineDto;
import com.biblioteca.backend.entities.Libro;
import com.biblioteca.backend.entities.Ordine;
import com.biblioteca.backend.entities.User;
import com.biblioteca.backend.exceptions.LibroNonEsistenteException;
import com.biblioteca.backend.exceptions.OrdineNonEsistenteException;
import com.biblioteca.backend.exceptions.UtenteNonEsitenteException;
import com.biblioteca.backend.repositories.LibroRepository;
import com.biblioteca.backend.repositories.OrdineRepository;
import com.biblioteca.backend.repositories.UserRepository;


@Service
public class OrdineService 
{
    private final OrdineRepository ordineRepository;
    private final UserRepository userRepository;
    private final LibroRepository libroRepository;

    public OrdineService(OrdineRepository ordineRepository, UserRepository userRepository, LibroRepository libroRepository)
    {
        this.ordineRepository = ordineRepository;
        this.userRepository = userRepository;
        this.libroRepository = libroRepository;
    }


    public Ordine getOrdine(Long id)
    {
        return ordineRepository.findById(id)
                .orElseThrow(() -> new OrdineNonEsistenteException("Ordine non trovato con id: "+id));
    }

    public OrdineDto trovaOrdine(Long id)
    {
        return ordineRepository.findById(id)
                .map(OrdineDto::new)
                .orElseThrow(() -> new OrdineNonEsistenteException("Ordine non trovato con id: "+id));
    }

    public Ordine creaOrdine(Long userId, Long libroId)
    {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UtenteNonEsitenteException("Nessun utente con id "+userId));

        Libro libro = libroRepository.findById(libroId)
                .orElseThrow(() -> new LibroNonEsistenteException("Nessun libro con id "+ libroId));

       if (libro.getUser() != null) 
       {
            throw new RuntimeException("Il libro è già in prestito"); 
       }
       
       libro.setUser(user); //assegna il libro all'utente trovato in precendenza

       Ordine ordine = new Ordine();
       ordine.setUser(user);
       ordine.setLibro(libro);
       ordine.setDataOrdine(LocalDate.now());

       ordineRepository.save(ordine);
       libroRepository.save(libro);

       return ordine;
    }


    public Ordine restituisciOrdine(Long ordineId)
    {
        Ordine ordine = ordineRepository.findById(ordineId)
                .orElseThrow(() -> new OrdineNonEsistenteException("Nessun ordine con id " + ordineId));
        
        ordine.setDataRestituzione(LocalDate.now());
        ordine.setAttivo(false); //ordine non più attivo

        Libro libro = ordine.getLibro();
        libro.setUser(null); //ora il libro non è più posseduto da alcun utente

        libroRepository.save(libro);
        ordineRepository.save(ordine);

        return ordine;
    }


    public List<OrdineDto> getOrdiniAttivi()
    {
        List<Ordine> ordiniAttivi = ordineRepository.findByAttivoTrue();

        if (ordiniAttivi.isEmpty()) 
        {
            throw new OrdineNonEsistenteException("Nessun ordine attivo");    
        }

        return ordiniAttivi
                .stream()
                .map(OrdineDto::new)
                .toList();
    }

     public List<OrdineDto> getOrdiniNonAttivi()
    {
        List<Ordine> ordiniNonAttivi = ordineRepository.findByAttivoFalse();

        if (ordiniNonAttivi.isEmpty()) 
        {
            throw new OrdineNonEsistenteException("Non ci sono ordini non attivi");    
        }

        return ordiniNonAttivi
                .stream()
                .map(OrdineDto::new)
                .toList();
    }

    public List<OrdineDto> getOrdiniConLibroEUtente()
    {
        List<Ordine> ordini = ordineRepository.findOrdiniConLibroEUtente();

        return ordini.stream().map(OrdineDto::new).toList();
    }
    

    public List<OrdineDto> getOrdineByUserIdELibroId(Long userId, Long libroId)
    {
        return ordineRepository.findOrdineByUserIdELibroId(userId, libroId)
                .stream()
                .map(OrdineDto::new)
                .toList();
    }
}
