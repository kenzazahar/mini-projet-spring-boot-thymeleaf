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
        if (eleve.getId() == null) {
            // Création d'un nouvel élève
            return createNewEleve(eleve);
        } else {
            // Modification d'un élève existant
            return updateEleve(eleve);
        }
    }

    private Eleve createNewEleve(Eleve eleve) {
        // Créer le dossier administratif
        DossierAdministratif dossier = new DossierAdministratif();
        dossier.setDateCreation(LocalDate.now());
        eleve.setDossierAdministratif(dossier);

        // Sauvegarder l'élève
        Eleve savedEleve = eleveRepository.save(eleve);

        // Générer le numéro d'inscription
        String numero = genererNumeroInscription(savedEleve);
        savedEleve.getDossierAdministratif().setNumeroInscription(numero);

        return eleveRepository.save(savedEleve);
    }

    private Eleve updateEleve(Eleve eleve) {
        // Récupérer l'élève existant avec toutes ses données
        Eleve existingEleve = eleveRepository.findById(eleve.getId())
                .orElseThrow(() -> new RuntimeException("Élève non trouvé avec l'id: " + eleve.getId()));

        // Mettre à jour uniquement les champs modifiables
        existingEleve.setNom(eleve.getNom());
        existingEleve.setPrenom(eleve.getPrenom());

        // Gérer le changement de filière
        boolean filiereChanged = false;

        if (eleve.getFiliere() != null) {
            // Nouvelle filière assignée
            if (existingEleve.getFiliere() == null ||
                    !eleve.getFiliere().getId().equals(existingEleve.getFiliere().getId())) {
                filiereChanged = true;
                existingEleve.setFiliere(eleve.getFiliere());
            }
        } else {
            // Filière retirée
            if (existingEleve.getFiliere() != null) {
                filiereChanged = true;
                existingEleve.setFiliere(null);
            }
        }

        // Mettre à jour le numéro d'inscription si la filière a changé
        if (filiereChanged && existingEleve.getDossierAdministratif() != null) {
            String nouveauNumero = genererNumeroInscription(existingEleve);
            existingEleve.getDossierAdministratif().setNumeroInscription(nouveauNumero);
        }

        return eleveRepository.save(existingEleve);
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
}