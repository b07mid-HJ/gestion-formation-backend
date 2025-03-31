package com.formation.formation.role;

import jakarta.persistence.*;
import java.util.UUID;

import com.formation.formation.utilisateur.Utilisateur;

import java.util.Set;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String nom;


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }


    public UUID getId() {
        return id;
    }

    public Role() {
    }

    public Role(String nom, Set<Utilisateur> utilisateurs) {
        this.nom = nom;
    }
}
