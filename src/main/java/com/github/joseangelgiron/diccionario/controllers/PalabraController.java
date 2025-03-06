package com.github.joseangelgiron.diccionario.controllers;

import com.github.joseangelgiron.diccionario.exceptions.PalabraNotFoundException;
import com.github.joseangelgiron.diccionario.models.Definicion;
import com.github.joseangelgiron.diccionario.models.Palabra;
import com.github.joseangelgiron.diccionario.servicies.DefinicionService;
import com.github.joseangelgiron.diccionario.servicies.PalabraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/palabras")
public class PalabraController {

    @Autowired
    private PalabraService palabraService;

    @Autowired
    private DefinicionService definicionService;

    /**
     * Retrieves all words from the database.
     *
     * @return A ResponseEntity containing a list of all words. If no words are found, returns an empty list
     */
    @CrossOrigin 
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> findAll() {
        List<Map<String, Object>> list = palabraService.getAllPalabras();
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }



    /**
     * Retrieves a word by its ID.
     *
     * @param id The ID of the word to retrieve.
     * @return A ResponseEntity containing the word with a status of 200 (OK) if found.
     * @throws PalabraNotFoundException if the word with the given ID does not exist.
     */
    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Palabra> getPalabraById(@PathVariable Integer id) throws PalabraNotFoundException {
        Palabra palabra = palabraService.getPalabraById(id);
        return new ResponseEntity<Palabra>(palabra, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * Retrieves a list of words by their grammatical category.
     *
     * @param categoria The grammatical category to filter the words by.
     * @return A ResponseEntity containing the list of words belonging to the specified category.
     *         If no words are found, returns 204 (No Content).
     */
    @CrossOrigin
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<Palabra>> getPalabrasByCategoria(@PathVariable String categoria) {
        List<Palabra> palabras = palabraService.getPalabrasByCategoria(categoria);

        if (palabras.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(palabras, HttpStatus.OK);
    }

    /**
     * Retrieves a list of words that start with the specified letter.
     *
     * @param letra The initial letter to filter the words by.
     * @return A ResponseEntity containing the list of words that start with the specified letter.
     *         If no words are found, returns 204 (No Content).
     * @throws PalabraNotFoundException if no words are found with the given initial letter.
     */
    @CrossOrigin
    @GetMapping("/inicial/{letra}")
    public ResponseEntity<List<Palabra>> getPalabrasByInitials(@PathVariable String letra) throws PalabraNotFoundException {
        List<Palabra> palabras = palabraService.getPalabrasByInitials(letra);

        if (palabras.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(palabras, HttpStatus.OK);
    }


    /**
     * Creates a new word.
     *
     * @param palabra The word to be created.
     * @return A ResponseEntity containing the created word with a status of 201 (Created).
     */
    @CrossOrigin
    @PostMapping
    public ResponseEntity<Palabra> createPalabra(@RequestBody Palabra palabra) {
        Palabra createdPalabra = palabraService.createPalabra(palabra);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPalabra);

    }

    /**
     * Creates a new word along with its associated definitions.
     *
     * @param palabra The word along with its definitions to be created.
     * @return A ResponseEntity containing the created word and definitions with a status of 201 (Created).
     */
    @CrossOrigin
    @PostMapping("/con-definiciones")
    public ResponseEntity<Palabra> createPalabraAndDefiniciones(@RequestBody Palabra palabra) {
        Palabra createdPalabra = palabraService.createPalabraAndDefiniciones(palabra);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPalabra);
    }

    /**
     * Updates an existing word.
     *
     * @param updatedPalabra The word object with updated values.
     * @return A ResponseEntity containing the updated word with a status of 200 (OK).
     * @throws PalabraNotFoundException if the word to be updated does not exist.
     */
    @CrossOrigin
    @PutMapping()
    public ResponseEntity<Palabra> updatePalabra(@RequestBody Palabra updatedPalabra) throws PalabraNotFoundException {
        Palabra palabraUpdated = palabraService.updatePalabra(updatedPalabra);
        return ResponseEntity.status(HttpStatus.OK).body(palabraUpdated);
    }


    /**
     * Deletes a word by its ID.
     *
     * @param id The ID of the word to be deleted.
     * @return A status of 204 (No Content) if the word was successfully deleted.
     * @throws PalabraNotFoundException if the word with the given ID does not exist.
     */
    @CrossOrigin
    @DeleteMapping("/{id}")
    public HttpStatus deletePalabrabyId(@PathVariable Integer id) throws PalabraNotFoundException {
        palabraService.deletePalabra(id);
        return HttpStatus.ACCEPTED;
    }








}
