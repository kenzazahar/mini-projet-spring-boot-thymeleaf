package com.example.school.repositories;

import com.example.school.entities.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FiliereRepository extends JpaRepository<Filiere, Long> {
    Optional<Filiere> findByCode(String code);
}