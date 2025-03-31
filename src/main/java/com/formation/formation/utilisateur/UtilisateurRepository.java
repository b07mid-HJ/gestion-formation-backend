package com.formation.formation.utilisateur;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, UUID> {
    Optional<Utilisateur> findById(UUID id);
    Optional<Utilisateur> findByLogin(String login);
    boolean existsByLogin(String login);
} 