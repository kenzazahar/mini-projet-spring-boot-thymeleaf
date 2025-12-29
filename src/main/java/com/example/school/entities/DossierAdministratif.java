package com.example.school.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DossierAdministratif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String numeroInscription;

    @Column(nullable = false)
    private LocalDate dateCreation;

    @OneToOne(mappedBy = "dossierAdministratif")
    private Eleve eleve;
}