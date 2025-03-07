package com.github.joseangelgiron.diccionario.servicies;

import com.github.joseangelgiron.diccionario.exceptions.PalabraNotFoundException;
import com.github.joseangelgiron.diccionario.models.Definicion;
import com.github.joseangelgiron.diccionario.models.Palabra;
import com.github.joseangelgiron.diccionario.repositories.DefinicionRepository;
import com.github.joseangelgiron.diccionario.repositories.PalabraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class DefinicionService {

    @Autowired
    private DefinicionRepository definicionRepository;
    @Autowired
    private PalabraRepository palabraRepository;

    /**
     * Retrieves the list of definitions associated with a specific word.
     *
     * @param palabraId The ID of the word whose definitions are to be retrieved.
     * @return A list of definitions if the word exists and has associated definitions;
     *         otherwise, returns an empty list.
     */
    public Palabra getDefinicionesByPalabra(Integer palabraId) {
        Optional<Palabra> palabra = palabraRepository.findById(palabraId);
        if(palabra.isPresent()) {

            return palabra.get();
        }

        return null;
    }

    /**
     * Adds a new definition to a specific word.
     *
     * @param palabraId The ID of the word to which the definition will be added.
     * @param definicion The definition object to be associated with the word.
     * @return The saved definition after being associated with the word.
     * @throws PalabraNotFoundException if the word with the given ID does not exist.
     */
    public Definicion addDefinicion(Integer palabraId, Definicion definicion) {
        Optional<Palabra> palabra = palabraRepository.findById(palabraId);
        if (palabra.isPresent()) {
            definicion.setPalabra(palabra.get());
            return definicionRepository.save(definicion);
        }
        throw new PalabraNotFoundException("Palabra no encontrada", 1);
    }

    /**
     * Deletes a definition by its ID.
     *
     * @param id The ID of the definition to be deleted.
     * @return {@code true} if the definition was found and deleted, {@code false} otherwise.
     */
    public boolean deleteDefinicion(Integer id) {
        boolean deleted = false;
        Optional<Definicion> definicionOptional = definicionRepository.findDefinicionById(id);
        if(definicionOptional.isPresent()) {
            definicionRepository.deleteById(id);
            deleted = true;
        }
        return deleted;
    }
}
