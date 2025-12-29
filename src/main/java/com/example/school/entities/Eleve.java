package com.example.school.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Eleve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "dossier_id", referencedColumnName = "id")
    private DossierAdministratif dossierAdministratif;

    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;

    @ManyToMany
    @JoinTable(
            name = "eleve_cours",
            joinColumns = @JoinColumn(name = "eleve_id"),
            inverseJoinColumns = @JoinColumn(name = "cours_id")
    )
    private List<Cours> cours = new ArrayList<>();
}