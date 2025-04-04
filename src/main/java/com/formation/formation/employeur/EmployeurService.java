package com.formation.formation.employeur;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

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
    
    public Employeur addEmployeur(Employeur employeur) {
        // Check if employeur with same name already exists
        Optional<Employeur> existingEmployeur = employeurRepository.findByNomemployeur(employeur.getNomemployeur());
        
        if (existingEmployeur.isPresent()) {
            throw new RuntimeException("An employeur with this name already exists");
        }
        
        employeurRepository.save(employeur);
        return employeur;
    }

    @Transactional
    public Employeur updateEmployeur(Employeur employeur) {
        Optional<Employeur> employeurExist = employeurRepository.findById(employeur.getId());
        
        if (employeurExist.isPresent()) {
            Employeur employeurToUpdate = employeurExist.get();
            
            // Check if another employeur with the new name already exists
            if (!employeurToUpdate.getNomemployeur().equals(employeur.getNomemployeur())) {
                Optional<Employeur> duplicateEmployeur = employeurRepository.findByNomemployeur(employeur.getNomemployeur());
                
                if (duplicateEmployeur.isPresent() && !duplicateEmployeur.get().getId().equals(employeur.getId())) {
                    throw new RuntimeException("Another employeur with this name already exists");
                }
            }
            
            employeurToUpdate.setNomemployeur(employeur.getNomemployeur());
            
            employeurRepository.save(employeurToUpdate);
            return employeurToUpdate;
        }
        
        return null;
    }

    @Transactional
    public void deleteEmployeur(UUID id) {
        employeurRepository.deleteById(id);
    }
}
