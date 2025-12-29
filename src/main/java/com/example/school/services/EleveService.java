package com.example.school.services;

import com.example.school.entities.DossierAdministratif;
import com.example.school.entities.Eleve;
import com.example.school.repositories.EleveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class EleveService {

    @Autowired
    private EleveRepository eleveRepository;

    public List<Eleve> getAllEleves() {
        return eleveRepository.findAll();
    }

    public Eleve saveEleve(Eleve eleve) {
        // Créer le dossier administratif si c'est un nouvel élève
        if (eleve.getId() == null && eleve.getDossierAdministratif() == null) {
            DossierAdministratif dossier = new DossierAdministratif();
            dossier.setDateCreation(LocalDate.now());
            eleve.setDossierAdministratif(dossier);
        }

        // Sauvegarder l'élève
        Eleve savedEleve = eleveRepository.save(eleve);

        // Générer le numéro d'inscription si ce n'est pas déjà fait
        if (savedEleve.getDossierAdministratif().getNumeroInscription() == null) {
            String numero = genererNumeroInscription(savedEleve);
            savedEleve.getDossierAdministratif().setNumeroInscription(numero);
            savedEleve = eleveRepository.save(savedEleve);
        }

        return savedEleve;
    }

    private String genererNumeroInscription(Eleve eleve) {
        String codeFiliere = eleve.getFiliere() != null ?
                eleve.getFiliere().getCode() : "NONE";
        int annee = LocalDate.now().getYear();
        Long id = eleve.getId();
        return String.format("%s-%d-%d", codeFiliere, annee, id);
    }

    public Eleve getEleveById(Long id) {
        return eleveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'id: " + id));
    }

    public void deleteEleve(Long id) {
        if (!eleveRepository.existsById(id)) {
            throw new RuntimeException("Élève non trouvé avec l'id: " + id);
        }
        eleveRepository.deleteById(id);
    }

    public List<Eleve> getElevesByFiliere(Long filiereId) {
        return eleveRepository.findByFiliereId(filiereId);
    }

    public Eleve updateEleve(Long id, Eleve eleve) {
        Eleve existingEleve = getEleveById(id);
        existingEleve.setNom(eleve.getNom());
        existingEleve.setPrenom(eleve.getPrenom());

        // Si la filière a changé, mettre à jour le numéro d'inscription
        if (eleve.getFiliere() != null &&
                !eleve.getFiliere().equals(existingEleve.getFiliere())) {
            existingEleve.setFiliere(eleve.getFiliere());
            String nouveauNumero = genererNumeroInscription(existingEleve);
            existingEleve.getDossierAdministratif().setNumeroInscription(nouveauNumero);
        }

        return eleveRepository.save(existingEleve);
    }
}