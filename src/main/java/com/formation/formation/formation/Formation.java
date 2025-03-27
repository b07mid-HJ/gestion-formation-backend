package com.formation.formation.formation;

import jakarta.persistence.*;
import java.util.UUID;

import com.formation.formation.domaine.Domaine;
import com.formation.formation.formateur.Formateur;
import com.formation.formation.participant.Participant;

import java.util.Set;

@Entity
@Table(name = "formation")
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String titre;

    @Column(nullable = false)
    private Integer annee;

    @Column(nullable = false)
    private Integer duree;

    @Column(precision = 10)
    private Double budget;

    @ManyToOne
    @JoinColumn(name = "id_domaine")
    private Domaine domaine;

    @ManyToOne
    @JoinColumn(name = "id_formateur")
    private Formateur formateur;

    @ManyToMany
    @JoinTable(
        name = "formation_participant",
        joinColumns = @JoinColumn(name = "id_formation"),
        inverseJoinColumns = @JoinColumn(name = "id_participant")
    )
    private Set<Participant> participants;

    public Formation() {
    }

    public Formation(String titre, Integer annee, Integer duree, Double budget, Domaine domaine, Formateur formateur,
            Set<Participant> participants) {
        this.titre = titre;
        this.annee = annee;
        this.duree = duree;
        this.budget = budget;
        this.domaine = domaine;
        this.formateur = formateur;
        this.participants = participants;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Integer getAnnee() {
        return annee;
    }

    public void setAnnee(Integer annee) {
        this.annee = annee;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    public Double getBudget() {
        return budget;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }

    public Domaine getDomaine() {
        return domaine;
    }

    public void setDomaine(Domaine domaine) {
        this.domaine = domaine;
    }

    public Formateur getFormateur() {
        return formateur;
    }

    public void setFormateur(Formateur formateur) {
        this.formateur = formateur;
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
