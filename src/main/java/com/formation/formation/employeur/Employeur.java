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

    @OneToMany(mappedBy = "employeur")
    private Set<Formateur> formateurs;

    public Employeur() {
    }

    public Employeur(String nomemployeur, Set<Formateur> formateurs) {
        this.nomemployeur = nomemployeur;
        this.formateurs = formateurs;
    }

    public String getNomemployeur() {
        return nomemployeur;
    }

    public void setNomemployeur(String nomemployeur) {
        this.nomemployeur = nomemployeur;
    }

    public Set<Formateur> getFormateurs() {
        return formateurs;
    }

    public void setFormateurs(Set<Formateur> formateurs) {
        this.formateurs = formateurs;
    }

    public UUID getId() {
        return id;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
}
