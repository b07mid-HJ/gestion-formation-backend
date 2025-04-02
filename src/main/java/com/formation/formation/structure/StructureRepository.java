package com.formation.formation.structure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StructureRepository extends JpaRepository<Structure, UUID> {
    Optional<Structure> findById(UUID id);
    Optional<Structure> findByLibelle(String libelle);
} 