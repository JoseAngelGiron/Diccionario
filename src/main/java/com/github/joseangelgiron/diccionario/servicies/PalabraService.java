package com.github.joseangelgiron.diccionario.servicies;

import com.github.joseangelgiron.diccionario.exceptions.PalabraNotFoundException;
import com.github.joseangelgiron.diccionario.models.Palabra;
import com.github.joseangelgiron.diccionario.repositories.PalabraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PalabraService {

    @Autowired
    private PalabraRepository palabraRepository;

    public List<Palabra> getAllPalabras() {
        List<Palabra> PalabrasList = palabraRepository.findAll();
        if(!PalabrasList.isEmpty()){
            return PalabrasList;
        }else{
            return new ArrayList<Palabra>();
        }
    }

    public Palabra getPalabraById(Integer id) throws PalabraNotFoundException {
        Optional<Palabra> Palabra = palabraRepository.findById(id);
        if(Palabra.isPresent()){
            return Palabra.get();
        }else{
            throw new PalabraNotFoundException("  ",id);
        }
    }

    public Palabra createPalabra(Palabra Palabra)  {
        Palabra = palabraRepository.save(Palabra);
        return Palabra;
    }

    public Palabra updatePalabra(Palabra Palabra)  throws PalabraNotFoundException {
        if (Palabra.getId()!=null){
            Optional<Palabra> PalabraOptional = palabraRepository.findById(Palabra.getId());
            if (PalabraOptional.isPresent()){
                Palabra newPalabra = PalabraOptional.get();
                newPalabra.setTermino(Palabra.getTermino());
                newPalabra.setCategoriaGramatical(Palabra.getCategoriaGramatical());

                newPalabra=palabraRepository.save(newPalabra);
                return newPalabra;
            }else{
                throw new PalabraNotFoundException("No existe Palabra para el id: ",Palabra.getId());
            }
        }else{
            throw new PalabraNotFoundException("No hay id en la palabra a actualizar ",0l);
        }
    }



    public void deletePalabra(Integer id) throws PalabraNotFoundException {
        Optional<Palabra> PalabraOptional = palabraRepository.findById(id);
        if (PalabraOptional.isPresent()){
            palabraRepository.delete(PalabraOptional.get());
        }else{
            throw new PalabraNotFoundException("No existe Palabra para el id: ",id);
        }
    }


}
