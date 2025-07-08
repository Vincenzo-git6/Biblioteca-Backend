package com.biblioteca.backend.exceptions;

public class UtenteNonEsitenteException extends RuntimeException
{
    public UtenteNonEsitenteException(String message)
    {
        super(message);
    }    
}
