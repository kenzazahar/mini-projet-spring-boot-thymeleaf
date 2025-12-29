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
public class Filiere {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String nom;

    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL)
    private List<Eleve> eleves = new ArrayList<>();

    @OneToMany(mappedBy = "filiere", cascade = CascadeType.ALL)
    private List<Cours> cours = new ArrayList<>();
}