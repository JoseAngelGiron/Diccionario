package com.github.joseangelgiron.diccionario.controllers;

import com.github.joseangelgiron.diccionario.exceptions.PalabraNotFoundException;
import com.github.joseangelgiron.diccionario.models.Definicion;
import com.github.joseangelgiron.diccionario.models.Palabra;
import com.github.joseangelgiron.diccionario.servicies.DefinicionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/definiciones")
public class DefinicionController {

    @Autowired
    private DefinicionService definicionService;

    /**
     * Deletes a definition by its ID.
     *
     * @param id The ID of the definition to delete.
     * @return A status indicating the outcome of the operation.
     *         - 204 (No Content) if deletion was successful.
     *         - 404 (Not Found) if the definition with the given ID was not found.
     */
    @DeleteMapping("/{id}")
    public HttpStatus deleteDefinicion(@PathVariable Integer id) {

        boolean deleted = definicionService.deleteDefinicion(id);

        if (deleted) {
            return HttpStatus.ACCEPTED;
        }else {
            return HttpStatus.BAD_REQUEST;
        }
    }


    /**
     * Adds a new definition to a specific word.
     *
     * @param id The ID of the word to which the definition is being added.
     * @param definicion The definition to be added.
     * @return A ResponseEntity containing the created definition, with a status of 201 (Created).
     * @throws PalabraNotFoundException if the word with the given ID does not exist.
     */
    @CrossOrigin
    @PostMapping("/palabra/{id}")
    public ResponseEntity<Definicion> addDefinicion(@PathVariable Integer id, @RequestBody Definicion definicion) {
        try {
            Definicion nuevaDefinicion = definicionService.addDefinicion(id, definicion);
            return new ResponseEntity<>(nuevaDefinicion, HttpStatus.CREATED);
        } catch (PalabraNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Retrieves all definitions with his word for a specific word by its ID.
     *
     * @param id The ID of the word for which definitions are being fetched.
     * @return A ResponseEntity containing a list of definitions, with an HTTP status of 200 (OK)
     */
    @CrossOrigin
    @GetMapping("/palabra/{id}")
    public ResponseEntity<Palabra> findPalabraAndDefinitions(@PathVariable Integer id) {
        Palabra palabra = definicionService.getDefinicionesByPalabra(id);
        return new ResponseEntity<>(palabra, HttpStatus.OK);
    }


}
