package com.github.joseangelgiron.diccionario.exceptions;

public class PalabraNotFoundException extends RuntimeException {
    public PalabraNotFoundException(String message) {
        super(message);
    }
}
