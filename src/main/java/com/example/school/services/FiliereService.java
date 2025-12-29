package com.example.school.services;

import com.example.school.entities.Filiere;
import com.example.school.repositories.FiliereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class FiliereService {

    @Autowired
    private FiliereRepository filiereRepository;

    public List<Filiere> getAllFilieres() {
        return filiereRepository.findAll();
    }

    public Filiere saveFiliere(Filiere filiere) {
        return filiereRepository.save(filiere);
    }

    public Filiere getFiliereById(Long id) {
        return filiereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Filière non trouvée avec l'id: " + id));
    }

    public void deleteFiliere(Long id) {
        if (!filiereRepository.existsById(id)) {
            throw new RuntimeException("Filière non trouvée avec l'id: " + id);
        }
        filiereRepository.deleteById(id);
    }

    public Filiere updateFiliere(Long id, Filiere filiere) {
        Filiere existingFiliere = getFiliereById(id);
        existingFiliere.setCode(filiere.getCode());
        existingFiliere.setNom(filiere.getNom());
        return filiereRepository.save(existingFiliere);
    }
}