package com.github.joseangelgiron.diccionario.repositories;

import com.github.joseangelgiron.diccionario.models.Definicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DefinicionRepository extends JpaRepository<Definicion, Integer> {

    @Query(
            value = "SELECT * FROM definicion d WHERE d.id = ?1",
            nativeQuery = true
    )
    Optional<Definicion> findDefinicionById(Integer id);


}
