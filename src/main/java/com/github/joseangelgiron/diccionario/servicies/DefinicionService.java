package com.github.joseangelgiron.diccionario.servicies;

import com.github.joseangelgiron.diccionario.exceptions.PalabraNotFoundException;
import com.github.joseangelgiron.diccionario.models.Definicion;
import com.github.joseangelgiron.diccionario.models.Palabra;
import com.github.joseangelgiron.diccionario.repositories.DefinicionRepository;
import com.github.joseangelgiron.diccionario.repositories.PalabraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DefinicionService {

    @Autowired
    private DefinicionRepository definicionRepository;

    @Autowired
    private PalabraRepository palabraRepository;

    public List<Definicion> getDefinicionesByPalabra(Integer palabraId) {
        Optional<Palabra> palabra = palabraRepository.findById(palabraId);
        if(palabra.isPresent()) {
            List<Definicion> definiciones = definicionRepository.getDefinicionesByPalabraId(palabraId);
            if(definiciones != null) {
                return definiciones;
            }
        }

        return new ArrayList<>();
    }

    public Definicion addDefinicion(Integer palabraId, Definicion definicion) {
        Optional<Palabra> palabra = palabraRepository.findById(palabraId);
        if (palabra.isPresent()) {
            definicion.setPalabra(palabra.get());
            return definicionRepository.save(definicion);
        }
        throw new PalabraNotFoundException("Palabra no encontrada", 1);
    }

    public void deleteDefinicion(Integer id) {
        definicionRepository.deleteById(id);
    }
}
