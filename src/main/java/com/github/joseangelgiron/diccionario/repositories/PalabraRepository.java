package com.github.joseangelgiron.diccionario.repositories;


import com.github.joseangelgiron.diccionario.models.Palabra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PalabraRepository extends JpaRepository<Palabra, Integer> {


}
