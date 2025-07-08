package com.biblioteca.backend.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.backend.dtos.OrdineDto;
import com.biblioteca.backend.dtos.OrdineRequest;
import com.biblioteca.backend.entities.Ordine;
import com.biblioteca.backend.exceptions.OrdineNonEsistenteException;
import com.biblioteca.backend.services.OrdineService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/admin/ordine")
@RequiredArgsConstructor
public class AdminOrdineController 
{
    private final OrdineService ordineService;

    @GetMapping("/attivi")
    public List<OrdineDto> getOrdiniAttivi()
    {
        return ordineService.getOrdiniAttivi();
    }

    @GetMapping("/nonattivi")
    public List<OrdineDto> getOrdiniNonAttivi()
    {
        return ordineService.getOrdiniNonAttivi();
    }

    @PostMapping("/inserisci")
    public ResponseEntity<?> creaOrdine(@RequestBody OrdineRequest request)
    {
        ordineService.creaOrdine(request.getUserId(), request.getLibroId());

        return ResponseEntity.ok().body(Map.of("message","Ordine creato con successo!"));
    }
    
    @PostMapping("/restituisci/{ordineId}")
    public ResponseEntity<?> restituisciOrdine(@PathVariable Long ordineId)
    {
        ordineService.restituisciOrdine(ordineId);

        Ordine ordine = ordineService.getOrdine(ordineId);
        String titoloLibroOrdine = ordine.getLibro().getTitolo();
        String nomeUtenteOrdine = ordine.getUser().getUsername();
        String messaggio = "Ordine con id "+ordineId+" restituito, il libro '"+titoloLibroOrdine+"' è stato restituito da "+nomeUtenteOrdine;

        return ResponseEntity.ok().body(Map.of("message", messaggio));
    }

    @GetMapping("/tutto")
    public List<OrdineDto> prendiTutto()
    {
        return ordineService.getOrdiniConLibroEUtente();
    }

    @PostMapping("/trovatramiteid")
    public List<OrdineDto> getOrdineByUserIdELibroId(@RequestBody OrdineRequest request)
    {
        List<OrdineDto> ordini = ordineService.getOrdineByUserIdELibroId(request.getUserId(), request.getLibroId());

        if (ordini.isEmpty()) {
            throw new OrdineNonEsistenteException("Nessun ordine trovato con userid: "+request.getUserId()+" e libroid: "+request.getLibroId());
        }

        return ordini;
    }
}
