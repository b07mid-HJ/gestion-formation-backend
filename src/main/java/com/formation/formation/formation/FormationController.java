package com.formation.formation.formation;

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
@RequestMapping(path = "api/formation")
public class FormationController {

    private final FormationService formationService;

    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }

    @GetMapping()
    public List<Formation> getFormations() {
        return formationService.getFormations();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Formation> getFormationById(@PathVariable UUID id) {
        try {
            Formation formation = formationService.getFormationById(id);
            return ResponseEntity.ok(formation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Object> createFormation(@RequestBody FormationDTO formationDTO) {
        try {
            Formation createdFormation = formationService.addFormationWithParticipantIds(formationDTO);
            return new ResponseEntity<>(createdFormation, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PutMapping
    public ResponseEntity<Object> updateFormation(@RequestBody FormationDTO formationDTO) {
        try {
            Formation updatedFormation = formationService.updateFormationWithParticipantIds(formationDTO);
            if (updatedFormation != null) {
                return ResponseEntity.ok(updatedFormation);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Formation not found");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable UUID id) {
        try {
            // Check if the formation exists
            formationService.getFormationById(id);
            
            formationService.deleteFormation(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}