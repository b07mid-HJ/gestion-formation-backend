package com.formation.formation.formateur;

import jakarta.persistence.*;
import java.util.UUID;

import com.formation.formation.employeur.Employeur;
import com.formation.formation.formation.Formation;

import java.util.Set;

@Entity
@Table(name = "formateur")
public class Formateur {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String nom;

    @Column(nullable = false, length = 50)
    private String prenom;

    @Column(length = 100)
    private String email;

    @Column(length = 20)
    private String tel;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private FormateurType type;

    @ManyToOne
    @JoinColumn(name = "id_employeur")
    private Employeur employeur;


    public enum FormateurType {
        interne, externe
    }

    public Formateur() {
    }

    public Formateur(String nom, String prenom, String email, String tel, FormateurType type, Employeur employeur,
            Set<Formation> formations) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.tel = tel;
        this.type = type;
        this.employeur = employeur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public FormateurType getType() {
        return type;
    }

    public void setType(FormateurType type) {
        this.type = type;
    }

    public Employeur getEmployeur() {
        return employeur;
    }

    public void setEmployeur(Employeur employeur) {
        this.employeur = employeur;
    }

    public UUID getId() {
        return id;
    }
    
}
