package com.formation.formation.employeur;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class EmployeurService {

    private final EmployeurRepository employeurRepository;

    public EmployeurService(EmployeurRepository employeurRepository) {
        this.employeurRepository = employeurRepository;
    }

    public List<Employeur> getEmployeurs() {
        return employeurRepository.findAll();
    }

    public Employeur getEmployeurById(UUID id) {
        return employeurRepository.findById(id).orElseThrow(() -> new RuntimeException("Employeur not found with id: " + id));
    }

}
