package com.example.school.services;

import com.example.school.entities.Cours;
import com.example.school.repositories.CoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CoursService {

    @Autowired
    private CoursRepository coursRepository;

    public List<Cours> getAllCours() {
        return coursRepository.findAll();
    }

    public Cours saveCours(Cours cours) {
        return coursRepository.save(cours);
    }

    public Cours getCoursById(Long id) {
        return coursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cours non trouvé avec l'id: " + id));
    }

    public void deleteCours(Long id) {
        if (!coursRepository.existsById(id)) {
            throw new RuntimeException("Cours non trouvé avec l'id: " + id);
        }
        coursRepository.deleteById(id);
    }

    public List<Cours> getCoursByFiliere(Long filiereId) {
        return coursRepository.findByFiliereId(filiereId);
    }

    public Cours updateCours(Long id, Cours cours) {
        Cours existingCours = getCoursById(id);
        existingCours.setCode(cours.getCode());
        existingCours.setIntitule(cours.getIntitule());
        existingCours.setFiliere(cours.getFiliere());
        return coursRepository.save(existingCours);
    }
}