package com.formation.formation.formateur;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.formation.formation.employeur.EmployeurService;

import jakarta.transaction.Transactional;

@Component
public class FormateurService {

    private final FormateurRepository formateurRepository;
    private final EmployeurService employeurService;

    public FormateurService(FormateurRepository formateurRepository, EmployeurService employeurService) {
        this.formateurRepository = formateurRepository;
        this.employeurService = employeurService;
    }

    public List<Formateur> getFormateurs() {
        return formateurRepository.findAll();
    }

    public Formateur getFormateurById(UUID id) {
        return formateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Formateur not found with id: " + id));
    }

    public Formateur addFormateur(Formateur formateur) {
        // Check if formateur with same email, nom and prenom already exists
        Optional<Formateur> existingFormateur = formateurRepository.findByEmailAndNomAndPrenom(
                formateur.getEmail(), formateur.getNom(), formateur.getPrenom());
        
        if (existingFormateur.isPresent()) {
            throw new RuntimeException("A formateur with this email, name and surname already exists");
        }
        
        // Verify that the employeur exists if provided
        if (formateur.getEmployeur() != null && formateur.getEmployeur().getId() != null) {
            employeurService.getEmployeurById(formateur.getEmployeur().getId());
        }
        
        formateurRepository.save(formateur);
        return formateur;
    }

    @Transactional
    public Formateur updateFormateur(Formateur formateur) {
        Optional<Formateur> formateurExist = formateurRepository.findById(formateur.getId());
        
        if (formateurExist.isPresent()) {
            Formateur formateurToUpdate = formateurExist.get();
            
            // Check if another formateur with the new email, nom and prenom already exists
            if (!formateurToUpdate.getEmail().equals(formateur.getEmail()) || 
                !formateurToUpdate.getNom().equals(formateur.getNom()) || 
                !formateurToUpdate.getPrenom().equals(formateur.getPrenom())) {
                
                Optional<Formateur> duplicateFormateur = formateurRepository.findByEmailAndNomAndPrenom(
                        formateur.getEmail(), formateur.getNom(), formateur.getPrenom());
                
                if (duplicateFormateur.isPresent() && !duplicateFormateur.get().getId().equals(formateur.getId())) {
                    throw new RuntimeException("Another formateur with this email, name and surname already exists");
                }
            }
            
            // Verify that the employeur exists if it's being updated
            if (formateur.getEmployeur() != null && formateur.getEmployeur().getId() != null) {
                employeurService.getEmployeurById(formateur.getEmployeur().getId());
            }
            
            formateurToUpdate.setNom(formateur.getNom());
            formateurToUpdate.setPrenom(formateur.getPrenom());
            formateurToUpdate.setEmail(formateur.getEmail());
            formateurToUpdate.setTel(formateur.getTel());
            formateurToUpdate.setType(formateur.getType());
            formateurToUpdate.setEmployeur(formateur.getEmployeur());
            
            formateurRepository.save(formateurToUpdate);
            return formateurToUpdate;
        }
        
        return null;
    }

    @Transactional
    public void deleteFormateur(UUID id) {
        formateurRepository.deleteById(id);
    }
} 