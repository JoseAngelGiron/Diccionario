package com.github.joseangelgiron.diccionario.controllers;

import com.github.joseangelgiron.diccionario.exceptions.PalabraNotFoundException;
import com.github.joseangelgiron.diccionario.models.Palabra;
import com.github.joseangelgiron.diccionario.servicies.PalabraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/palabras")
public class PalabraController {

    @Autowired
    private PalabraService palabraService;

    @CrossOrigin 
    @GetMapping
    public ResponseEntity<List<Palabra>> findAll() {
        List<Palabra> list = palabraService.getAllPalabras();
        return new ResponseEntity<List<Palabra>>(list, new HttpHeaders(), HttpStatus.OK);
    }


    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Palabra> getPalabraById(@PathVariable Integer id) throws PalabraNotFoundException {
        Palabra Palabra = palabraService.getPalabraById(id);
        return new ResponseEntity<Palabra>(Palabra, new HttpHeaders(), HttpStatus.OK);
    }


    @CrossOrigin
    @PostMapping
    public ResponseEntity<Palabra> createPalabra(@RequestBody Palabra Palabra) {
        Palabra createdPalabra = palabraService.createPalabra(Palabra);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPalabra);

    }

    @CrossOrigin
    @PutMapping()
    public ResponseEntity<Palabra> updatePalabra(@RequestBody Palabra updatedPalabra) throws PalabraNotFoundException {
        Palabra PalabraUpdated = palabraService.updatePalabra(updatedPalabra);
        return ResponseEntity.status(HttpStatus.OK).body(PalabraUpdated);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public HttpStatus deletePalabrabyId(@PathVariable Integer id) throws PalabraNotFoundException {
        palabraService.deletePalabra(id);
        return HttpStatus.ACCEPTED;
    }






}
