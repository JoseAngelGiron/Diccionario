package com.github.joseangelgiron.diccionario.servicies;

import com.github.joseangelgiron.diccionario.exceptions.PalabraNotFoundException;
import com.github.joseangelgiron.diccionario.models.Definicion;
import com.github.joseangelgiron.diccionario.models.Palabra;
import com.github.joseangelgiron.diccionario.repositories.DefinicionRepository;
import com.github.joseangelgiron.diccionario.repositories.PalabraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PalabraService {

    @Autowired
    private PalabraRepository palabraRepository;

    @Autowired
    private DefinicionRepository definicionRepository;


    /**
     * Retrieves all words from the database.
     *
     * @return A list of words represented as a map without definitions.
     */
    public List<Map<String, Object>> getAllPalabras() {
        List<Palabra> palabrasList = palabraRepository.findAll();

        return palabrasList.stream()
                .map(p -> {
                    Map<String, Object> palabraMap = new HashMap<>();
                    palabraMap.put("id", p.getId());
                    palabraMap.put("termino", p.getTermino());
                    palabraMap.put("categoriaGramatical", p.getCategoriaGramatical());
                    return palabraMap;
                })
                .collect(Collectors.toList());
    }


    /**
     * Retrieves a word by its ID.
     *
     * @param id The ID of the word to retrieve.
     * @return The word if found.
     * @throws PalabraNotFoundException if no word is found with the given ID.
     */
    public Palabra getPalabraById(Integer id) throws PalabraNotFoundException { //HACER SIN DEFINICIONES
        Optional<Palabra> Palabra = palabraRepository.findById(id);
        if(Palabra.isPresent()){
            return Palabra.get();
        }else{
            throw new PalabraNotFoundException("No existe una palabra para el id ",id);
        }
    }

    /**
     * Creates and saves a new word in the database.
     *
     * @param palabra The word entity to be saved.
     * @return The saved word with its generated ID and other persisted details.
     */
    public Palabra createPalabra(Palabra palabra)  {
        Palabra palabraSaved = palabraRepository.save(palabra);
        return palabraSaved;
    }

    /**
     * Creates a new word along with its associated definitions.
     *
     * @param palabra The word entity to be saved, including its definitions.
     * @return The saved word with its associated definitions persisted.
     */
    public Palabra createPalabraAndDefiniciones(Palabra palabra) {

        palabra = palabraRepository.save(palabra);

        Set<Definicion> definiciones = palabra.getDefiniciones();
        for (Definicion definicion : definiciones) {
            definicion.setPalabra(palabra);
        }
        definicionRepository.saveAll(definiciones);

        return palabra;
    }

    /**
     * Updates an existing word in the database.
     *
     * @param palabra The word entity containing updated values.
     * @return The updated word.
     * @throws PalabraNotFoundException if the word with the given ID is not found.
     * @throws IllegalArgumentException if the provided word or its ID is null.
     */
    public Palabra updatePalabra(Palabra palabra)  throws PalabraNotFoundException {
        Optional<Palabra> palabraOptional = palabraRepository.findById(palabra.getId());
        if (palabraOptional.isPresent()){

            Palabra newPalabra = palabraOptional.get();
            newPalabra.setTermino(palabra.getTermino());
            newPalabra.setCategoriaGramatical(palabra.getCategoriaGramatical());

            newPalabra=palabraRepository.save(newPalabra);
            return newPalabra;

        }else{
            throw new PalabraNotFoundException("No hay id en la palabra a actualizar ", 0L);
        }
    }


    /**
     * Deletes a word by its ID.
     *
     * @param id The ID of the word to delete.
     * @throws PalabraNotFoundException if no word is found with the given ID.
     */
    public void deletePalabra(Integer id) throws PalabraNotFoundException {
        Optional<Palabra> palabraOptional = palabraRepository.findById(id);
        if (palabraOptional.isPresent()){
            palabraRepository.delete(palabraOptional.get());
        }else{
            throw new PalabraNotFoundException("No existe Palabra para el id: ",id);
        }
    }

    /**
     * Retrieves a list of words by their grammatical category.
     *
     * @param category The grammatical category to filter the words by.
     * @return A list of words belonging to the specified category. If no words are found, returns an empty list.
     */
    public List<Palabra> getPalabrasByCategoria(String category) {
        List<Palabra> palabras = palabraRepository.getPalabrasByCategoriaGramatical(category);
        return palabras;
    }

    /**
     * Retrieves a list of words that start with the specified letter.
     *
     * @param letra The initial letter to filter the words by.
     * @return A list of words that start with the specified letter. If no words are found, returns an empty list.
     */
    public List<Palabra> getPalabrasByInitials(String letra) {
        List<Palabra> palabras = palabraRepository.getPalabrasByTerminoStartingWith(letra);
        return palabras;

    }
}
