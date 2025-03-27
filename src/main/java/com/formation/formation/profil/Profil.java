package com.formation.formation.profil;

import jakarta.persistence.*;
import java.util.UUID;

import com.formation.formation.participant.Participant;

import java.util.Set;

@Entity
@Table(name = "profil")
public class Profil {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String libelle;

    @OneToMany(mappedBy = "profil")
    private Set<Participant> participants;

    public Profil() {
    }

    public Profil(String libelle, Set<Participant> participants) {
        this.libelle = libelle;
        this.participants = participants;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Participant> participants) {
        this.participants = participants;
    }

    public UUID getId() {
        return id;
    }
}
