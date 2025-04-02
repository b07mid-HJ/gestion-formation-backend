package com.formation.formation.formateur;

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
@RequestMapping(path = "api/formateur")
public class FormateurController {

    private final FormateurService formateurService;

    public FormateurController(FormateurService formateurService) {
        this.formateurService = formateurService;
    }

    @GetMapping()
    public List<Formateur> getFormateurs() {
        return formateurService.getFormateurs();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Formateur> getFormateurById(@PathVariable UUID id) {
        try {
            Formateur formateur = formateurService.getFormateurById(id);
            return ResponseEntity.ok(formateur);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Object> createFormateur(@RequestBody Formateur formateur) {
        try {
            Formateur createdFormateur = formateurService.addFormateur(formateur);
            return new ResponseEntity<>(createdFormateur, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PutMapping
    public ResponseEntity<Object> updateFormateur(@RequestBody Formateur formateur) {
        try {
            Formateur updatedFormateur = formateurService.updateFormateur(formateur);
            if (updatedFormateur != null) {
                return ResponseEntity.ok(updatedFormateur);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Formateur not found");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormateur(@PathVariable UUID id) {
        try {
            // Check if the formateur exists
            formateurService.getFormateurById(id);
            
            formateurService.deleteFormateur(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 