package com.github.joseangelgiron.diccionario.controllers;

import com.github.joseangelgiron.diccionario.exceptions.PalabraNotFoundException;
import com.github.joseangelgiron.diccionario.models.Definicion;
import com.github.joseangelgiron.diccionario.models.Palabra;
import com.github.joseangelgiron.diccionario.servicies.DefinicionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/definiciones")
public class DefinicionController {

    @Autowired
    private DefinicionService definicionService;


    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una definición por ID",
            description = "Elimina una definición del sistema usando su ID. Devuelve un estado HTTP indicando el resultado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Definición eliminada exitosamente"),
            @ApiResponse(responseCode = "400", description = "No se pudo eliminar la definición, solicitud incorrecta")
    })
    public HttpStatus deleteDefinicion(@PathVariable Integer id) {

        boolean deleted = definicionService.deleteDefinicion(id);

        if (deleted) {
            return HttpStatus.ACCEPTED;
        } else {
            return HttpStatus.BAD_REQUEST;
        }
    }


    @CrossOrigin
    @PostMapping("/palabra/{id}")
    @Operation(summary = "Agregar una definición a una palabra",
            description = "Añade una nueva definición a una palabra existente mediante su ID. Si la palabra no se encuentra, devuelve un error.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Definición creada exitosamente",
                    content = @Content(schema = @Schema(implementation = Definicion.class))),
            @ApiResponse(responseCode = "404", description = "Palabra no encontrada")
    })
    public ResponseEntity<Definicion> addDefinicion(@PathVariable Integer id, @RequestBody Definicion definicion) {
        try {
            Definicion nuevaDefinicion = definicionService.addDefinicion(id, definicion);
            return new ResponseEntity<>(nuevaDefinicion, HttpStatus.CREATED);
        } catch (PalabraNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @CrossOrigin
    @GetMapping("/palabra/{id}")
    @Operation(summary = "Obtener una palabra y sus definiciones",
            description = "Busca una palabra por su ID y devuelve sus definiciones asociadas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Palabra encontrada exitosamente",
                    content = @Content(schema = @Schema(implementation = Palabra.class))),
            @ApiResponse(responseCode = "404", description = "Palabra no encontrada")
    })
    public ResponseEntity<Palabra> findPalabraAndDefinitions(@PathVariable Integer id) {
        Palabra palabra = definicionService.getDefinicionesByPalabra(id);
        return new ResponseEntity<>(palabra, HttpStatus.OK);
    }



}
