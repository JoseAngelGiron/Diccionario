package com.github.joseangelgiron.diccionario.controllers;

import com.github.joseangelgiron.diccionario.servicies.DefinicionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

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


}
