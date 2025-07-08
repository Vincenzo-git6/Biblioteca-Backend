package com.biblioteca.backend.exceptions;

public class LibroNonEsistenteException extends RuntimeException
{
    public LibroNonEsistenteException(String message)
    {
        super(message);
    }
    
}
