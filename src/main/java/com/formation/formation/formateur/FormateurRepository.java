package com.formation.formation.formateur;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormateurRepository extends JpaRepository<Formateur, UUID> {
    Optional<Formateur> findById(UUID id);
    Optional<Formateur> findByEmailAndNomAndPrenom(String email, String nom, String prenom);
} 