package com.github.joseangelgiron.diccionario.controllers;

import com.github.joseangelgiron.diccionario.exceptions.PalabraNotFoundException;
import com.github.joseangelgiron.diccionario.models.Palabra;
import com.github.joseangelgiron.diccionario.servicies.DefinicionService;
import com.github.joseangelgiron.diccionario.servicies.PalabraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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


    @GetMapping
    @Operation(
            summary = "Obtener todas las palabras",
            description = "Recupera una lista de todas las palabras almacenadas en la base de datos."
    )
    public ResponseEntity<List<Map<String, Object>>> findAll() {
        List<Map<String, Object>> list = palabraService.getAllPalabras();
        return new ResponseEntity<>(list, new HttpHeaders(), HttpStatus.OK);
    }




    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener una palabra por ID",
            description = "Recupera una palabra específica a partir de su identificador único."
    )
    public ResponseEntity<Palabra> getPalabraById(
            @Parameter(description = "ID de la palabra a buscar", required = true, example = "1")
            @PathVariable Integer id) throws PalabraNotFoundException {

        Palabra palabra = palabraService.getPalabraById(id);
        return new ResponseEntity<>(palabra, new HttpHeaders(), HttpStatus.OK);
    }


    @GetMapping("/categoria/{categoria}")
    @Operation(
            summary = "Obtener palabras por categoría",
            description = "Devuelve una lista de palabras que pertenecen a la categoría especificada."
    )
    public ResponseEntity<List<Palabra>> getPalabrasByCategoria(
            @Parameter(description = "Nombre de la categoría para filtrar palabras", required = true, example = "animales")
            @PathVariable String categoria) {

        List<Palabra> palabras = palabraService.getPalabrasByCategoria(categoria);

        if (palabras.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(palabras, HttpStatus.OK);
    }


    @GetMapping("/inicial/{letra}")
    @Operation(
            summary = "Obtener palabras por letra inicial",
            description = "Devuelve una lista de palabras que comienzan con la letra especificada."
    )
    public ResponseEntity<List<Palabra>> getPalabrasByInitials(
            @Parameter(description = "Letra inicial de las palabras a buscar", required = true, example = "A")
            @PathVariable String letra) throws PalabraNotFoundException {

        List<Palabra> palabras = palabraService.getPalabrasByInitials(letra);

        if (palabras.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(palabras, HttpStatus.OK);
    }


    @PostMapping
    @Operation(
            summary = "Crear una nueva palabra",
            description = "Guarda una nueva palabra en la base de datos y devuelve la palabra creada.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Palabra creada con éxito"),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
            }
    )
    public ResponseEntity<Palabra> createPalabra(
            @Parameter(description = "Objeto palabra a crear", required = true)
            @RequestBody Palabra palabra) {

        Palabra createdPalabra = palabraService.createPalabra(palabra);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPalabra);
    }


    @PostMapping("/con-definiciones")
    @Operation(
            summary = "Crear una palabra con definiciones",
            description = "Guarda una nueva palabra junto con sus definiciones en la base de datos y devuelve la palabra creada.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Palabra y definiciones creadas con éxito"),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida")
            }
    )
    public ResponseEntity<Palabra> createPalabraAndDefiniciones(
            @Parameter(description = "Objeto palabra con sus definiciones", required = true)
            @RequestBody Palabra palabra) {

        Palabra createdPalabra = palabraService.createPalabraAndDefiniciones(palabra);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPalabra);
    }


    @PutMapping
    @Operation(
            summary = "Actualizar una palabra",
            description = "Actualiza una palabra existente en la base de datos y devuelve la versión actualizada.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Palabra actualizada con éxito"),
                    @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
                    @ApiResponse(responseCode = "404", description = "Palabra no encontrada")
            }
    )
    public ResponseEntity<Palabra> updatePalabra(
            @Parameter(description = "Objeto palabra con los datos actualizados", required = true)
            @RequestBody Palabra updatedPalabra) throws PalabraNotFoundException {

        Palabra palabraUpdated = palabraService.updatePalabra(updatedPalabra);
        return ResponseEntity.status(HttpStatus.OK).body(palabraUpdated);
    }



    @CrossOrigin
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una palabra por ID",
            description = "Elimina una palabra del sistema usando su ID. Si la palabra no existe, lanza una excepción.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Palabra eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Palabra no encontrada",
                    content = @Content(schema = @Schema(implementation = PalabraNotFoundException.class)))
    })
    public HttpStatus deletePalabrabyId(@PathVariable Integer id) throws PalabraNotFoundException {
        palabraService.deletePalabra(id);
        return HttpStatus.ACCEPTED;
    }









}
