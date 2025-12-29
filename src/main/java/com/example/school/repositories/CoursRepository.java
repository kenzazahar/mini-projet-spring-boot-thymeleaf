package com.example.school.repositories;

import com.example.school.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
    List<Cours> findByFiliereId(Long filiereId);
}