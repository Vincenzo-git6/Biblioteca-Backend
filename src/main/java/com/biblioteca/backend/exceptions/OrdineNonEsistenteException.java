package com.biblioteca.backend.exceptions;

public class OrdineNonEsistenteException extends RuntimeException
{
    public OrdineNonEsistenteException(String message)
    {
        super(message);
    }    
}
