package com.formation.formation.participant;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, UUID> {
    Optional<Participant> findById(UUID id);
    Optional<Participant> findByEmailAndNomAndPrenom(String email, String nom, String prenom);
} 