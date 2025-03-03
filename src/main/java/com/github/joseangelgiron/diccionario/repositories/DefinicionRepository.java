package com.github.joseangelgiron.diccionario.repositories;

import com.github.joseangelgiron.diccionario.models.Definicion;
import com.github.joseangelgiron.diccionario.models.Palabra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DefinicionRepository extends JpaRepository<Definicion, Integer> {

    @Query(
            value = "SELECT * FROM definicion d WHERE d.palabra_id = ?1",
            nativeQuery = true
    )
    List<Definicion> getDefinicionesByPalabraId(Integer palabraId);

    Optional<Palabra> findDefinicionById(Integer id);
}
