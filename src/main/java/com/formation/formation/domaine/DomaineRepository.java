package com.formation.formation.domaine;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomaineRepository extends JpaRepository<Domaine, UUID> {
    Optional<Domaine> findById(UUID id);
    Optional<Domaine> findByLibelle(String libelle);
} 