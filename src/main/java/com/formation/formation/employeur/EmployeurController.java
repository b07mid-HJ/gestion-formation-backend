package com.formation.formation.employeur;

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
@RequestMapping(path = "api/employeur")
public class EmployeurController {

    private final EmployeurService employeurService;

    public EmployeurController(EmployeurService employeurService) {
        this.employeurService = employeurService;
    }

    @GetMapping()
    public List<Employeur> getEmployeurs() {
        return employeurService.getEmployeurs();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Employeur> getEmployeurById(@PathVariable UUID id) {
        try {
            Employeur employeur = employeurService.getEmployeurById(id);
            return ResponseEntity.ok(employeur);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Object> createEmployeur(@RequestBody Employeur employeur) {
        try {
            Employeur createdEmployeur = employeurService.addEmployeur(employeur);
            return new ResponseEntity<>(createdEmployeur, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PutMapping
    public ResponseEntity<Object> updateEmployeur(@RequestBody Employeur employeur) {
        try {
            Employeur updatedEmployeur = employeurService.updateEmployeur(employeur);
            if (updatedEmployeur != null) {
                return ResponseEntity.ok(updatedEmployeur);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employeur not found");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeur(@PathVariable UUID id) {
        try {
            // Check if the employeur exists
            employeurService.getEmployeurById(id);
            
            employeurService.deleteEmployeur(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 