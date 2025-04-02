package com.formation.formation.structure;

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
@RequestMapping(path = "api/structure")
public class StructureController {

    private final StructureService structureService;

    public StructureController(StructureService structureService) {
        this.structureService = structureService;
    }

    @GetMapping()
    public List<Structure> getStructures() {
        return structureService.getStructures();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Structure> getStructureById(@PathVariable UUID id) {
        try {
            Structure structure = structureService.getStructureById(id);
            return ResponseEntity.ok(structure);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Object> createStructure(@RequestBody Structure structure) {
        try {
            Structure createdStructure = structureService.addStructure(structure);
            return new ResponseEntity<>(createdStructure, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PutMapping
    public ResponseEntity<Structure> updateStructure(@RequestBody Structure structure) {
        Structure updatedStructure = structureService.updateStructure(structure);
        if (updatedStructure != null) {
            return ResponseEntity.ok(updatedStructure);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStructure(@PathVariable UUID id) {
        try {
            // Check if the structure exists
            structureService.getStructureById(id);
            
            structureService.deleteStructure(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 