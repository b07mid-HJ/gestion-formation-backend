package com.formation.formation.domaine;

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
@RequestMapping(path = "api/domaine")
public class DomaineController {

    private final DomaineService domaineService;

    public DomaineController(DomaineService domaineService) {
        this.domaineService = domaineService;
    }

    @GetMapping()
    public List<Domaine> getDomaines() {
        return domaineService.getDomaines();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Domaine> getDomaineById(@PathVariable UUID id) {
        try {
            Domaine domaine = domaineService.getDomaineById(id);
            return ResponseEntity.ok(domaine);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Object> createDomaine(@RequestBody Domaine domaine) {
        try{
            Domaine createdDomaine = domaineService.addDomaine(domaine);
            return new ResponseEntity<>(createdDomaine, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }
    
    @PutMapping
    public ResponseEntity<Domaine> updateDomaine(@RequestBody Domaine domaine) {
        Domaine updatedDomaine = domaineService.updateDomaine(domaine);
        if (updatedDomaine != null) {
            return ResponseEntity.ok(updatedDomaine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomaine(@PathVariable UUID id) {
        try {
            // Check if the domaine exists
            domaineService.getDomaineById(id);
            
            domaineService.deleteDomaine(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 