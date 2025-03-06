package com.github.joseangelgiron.diccionario.repositories;


import com.github.joseangelgiron.diccionario.models.Palabra;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PalabraRepository extends JpaRepository<Palabra, Integer> {




    List<Palabra> getPalabrasByTerminoStartingWith(String letra);

    List<Palabra> getPalabrasByCategoriaGramatical(@Size(max = 50) @NotNull String categoriaGramatical);
}
