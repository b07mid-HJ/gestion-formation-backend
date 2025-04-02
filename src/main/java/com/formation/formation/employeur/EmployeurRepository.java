package com.formation.formation.employeur;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeurRepository extends JpaRepository<Employeur, UUID> {

    Optional<Employeur> getEmployeurById(UUID id);

}
