package com.formation.formation.participant;

import java.util.Set;
import java.util.UUID;

import com.formation.formation.profil.Profil;
import com.formation.formation.structure.Structure;

public class ParticipantDTO {
    private UUID id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private Structure structure;
    private Profil profil;
    private Set<UUID> formationIds;

    public ParticipantDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Structure getStructure() {
        return structure;
    }

    public void setStructure(Structure structure) {
        this.structure = structure;
    }

    public Profil getProfil() {
        return profil;
    }

    public void setProfil(Profil profil) {
        this.profil = profil;
    }

    public Set<UUID> getFormationIds() {
        return formationIds;
    }

    public void setFormationIds(Set<UUID> formationIds) {
        this.formationIds = formationIds;
    }
}