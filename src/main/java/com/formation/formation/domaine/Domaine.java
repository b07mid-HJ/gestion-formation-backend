package com.formation.formation.domaine;
import jakarta.persistence.*;
import java.util.UUID;

import com.formation.formation.formation.Formation;

import java.util.Set;

@Entity
@Table(name = "domaine")
public class Domaine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String libelle;

    public Domaine() {
    }

    public Domaine(String libelle, Set<Formation> formations) {
        this.libelle = libelle;
        this.formations = formations;
    }

    @OneToMany(mappedBy = "domaine")
    private Set<Formation> formations;

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Set<Formation> getFormations() {
        return formations;
    }

    public void setFormations(Set<Formation> formations) {
        this.formations = formations;
    }

    public UUID getId() {
        return id;
    }
}
