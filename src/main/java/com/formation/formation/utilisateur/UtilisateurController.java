package com.formation.formation.utilisateur;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/utilisateur")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }

    @GetMapping()
    public List<Utilisateur> getUtilisateurs() {
        return utilisateurService.getUtilisateurs();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUtilisateurById(@PathVariable UUID id) {
        try {
            Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
            return ResponseEntity.ok(utilisateur);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping
    public ResponseEntity<Object> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur createdUtilisateur = utilisateurService.addUtilisateur(utilisateur);
            return new ResponseEntity<>(createdUtilisateur, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PutMapping
    public ResponseEntity<Object> updateUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur updatedUtilisateur = utilisateurService.updateUtilisateur(utilisateur);
            if (updatedUtilisateur != null) {
                return ResponseEntity.ok(updatedUtilisateur);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable UUID id) {
        try {
            // Check if the utilisateur exists
            utilisateurService.getUtilisateurById(id);
            
            utilisateurService.deleteUtilisateur(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 