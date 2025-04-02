package com.formation.formation.employeur;

import jakarta.persistence.*;
import java.util.UUID;

import com.formation.formation.formateur.Formateur;

import java.util.Set;

@Entity
@Table(name = "employeur")
public class Employeur {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String nomemployeur;


    public Employeur() {
    }

    public Employeur(String nomemployeur, Set<Formateur> formateurs) {
        this.nomemployeur = nomemployeur;
    }

    public String getNomemployeur() {
        return nomemployeur;
    }

    public void setNomemployeur(String nomemployeur) {
        this.nomemployeur = nomemployeur;
    }


    public UUID getId() {
        return id;
    }

}
