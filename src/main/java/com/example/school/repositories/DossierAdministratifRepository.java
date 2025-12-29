package com.example.school.repositories;

import com.example.school.entities.DossierAdministratif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DossierAdministratifRepository extends JpaRepository<DossierAdministratif, Long> {
}