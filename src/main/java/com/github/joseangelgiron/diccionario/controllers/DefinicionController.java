package com.github.joseangelgiron.diccionario.controllers;


import com.github.joseangelgiron.diccionario.models.Definicion;
import com.github.joseangelgiron.diccionario.models.Palabra;
import com.github.joseangelgiron.diccionario.servicies.DefinicionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/palabras")
public class DefinicionController {

    @Autowired
    private DefinicionService definicionService;

    @CrossOrigin
    @GetMapping("/{id}/definiciones")
    public ResponseEntity<List<Definicion>> findAll(@PathVariable Integer id) {
        List<Definicion> list = definicionService.getDefinicionesByPalabra(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/{id}/definiciones")
    public ResponseEntity<Definicion> addDefinicion(@PathVariable Integer id, @RequestBody Definicion definicion) {
        Definicion nuevaDefinicion = definicionService.addDefinicion(id, definicion);
        return new ResponseEntity<>(nuevaDefinicion, HttpStatus.CREATED);
    }

    @DeleteMapping("/definiciones/{id}")
    public HttpStatus deleteDefinicion(@PathVariable Integer id) {
        definicionService.deleteDefinicion(id);
        return HttpStatus.ACCEPTED;
    }


}
