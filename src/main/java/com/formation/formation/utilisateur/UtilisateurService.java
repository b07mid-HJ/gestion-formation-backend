package com.formation.formation.utilisateur;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.formation.formation.role.RoleService;

import jakarta.transaction.Transactional;

@Component
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final RoleService roleService;

    public UtilisateurService(UtilisateurRepository utilisateurRepository, RoleService roleService) {
        this.utilisateurRepository = utilisateurRepository;
        this.roleService = roleService;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Utilisateur getUtilisateurById(UUID id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur not found with id: " + id));
    }

    public Optional<Utilisateur> getUtilisateurByLogin(String login) {
        return utilisateurRepository.findByLogin(login);
    }

    public Utilisateur addUtilisateur(Utilisateur utilisateur) {
        // Check if login already exists
        if (utilisateurRepository.existsByLogin(utilisateur.getLogin())) {
            throw new RuntimeException("Login already exists: " + utilisateur.getLogin());
        }
        
        // Verify that the role exists
        if (utilisateur.getRole() != null && utilisateur.getRole().getId() != null) {
            roleService.getRoleById(utilisateur.getRole().getId());
        }
        
        // Here you would typically hash the password before saving
        // For example: utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        
        utilisateurRepository.save(utilisateur);
        return utilisateur;
    }

    @Transactional
    public Utilisateur updateUtilisateur(Utilisateur utilisateur) {
        Optional<Utilisateur> utilisateurExist = utilisateurRepository.findById(utilisateur.getId());
        
        if (utilisateurExist.isPresent()) {
            Utilisateur utilisateurToUpdate = utilisateurExist.get();
            
            // Check if login is being changed and if it already exists
            if (!utilisateurToUpdate.getLogin().equals(utilisateur.getLogin()) && 
                utilisateurRepository.existsByLogin(utilisateur.getLogin())) {
                throw new RuntimeException("Login already exists: " + utilisateur.getLogin());
            }
            
            // Verify that the role exists if it's being updated
            if (utilisateur.getRole() != null && utilisateur.getRole().getId() != null) {
                roleService.getRoleById(utilisateur.getRole().getId());
            }
            
            utilisateurToUpdate.setLogin(utilisateur.getLogin());
            
            // Only update password if it's provided (not null or empty)
            if (utilisateur.getPassword() != null && !utilisateur.getPassword().isEmpty()) {
                // Here you would typically hash the password before saving
                // For example: utilisateurToUpdate.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
                utilisateurToUpdate.setPassword(utilisateur.getPassword());
            }
            
            if (utilisateur.getRole() != null) {
                utilisateurToUpdate.setRole(utilisateur.getRole());
            }
            
            utilisateurRepository.save(utilisateurToUpdate);
            return utilisateurToUpdate;
        }
        
        return null;
    }

    @Transactional
    public void deleteUtilisateur(UUID id) {
        utilisateurRepository.deleteById(id);
    }
} 