package com.formation.formation.structure;

import jakarta.persistence.*;
import java.util.UUID;

import com.formation.formation.participant.Participant;

import java.util.Set;

@Entity
@Table(name = "structure")
public class Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String libelle;


    public Structure() {
    }

    public Structure(String libelle, Set<Participant> participants) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    public UUID getId() {
        return id;
    }

    
}