package com.formation.formation.profil;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfilRepository extends JpaRepository<Profil, UUID> {

    Optional<Profil> findById(UUID id);
    Optional<Profil> findByLibelle(String libelle);

}
