package com.formation.formation.structure;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class StructureService {

    private final StructureRepository structureRepository;

    public StructureService(StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }

    public List<Structure> getStructures() {
        return structureRepository.findAll();
    }

    public Structure getStructureById(UUID id) {
        return structureRepository.findById(id).orElseThrow(() -> new RuntimeException("Structure not found with id: " + id));
    }

    public Structure addStructure(Structure structure) {
        structureRepository.save(structure);
        return structure;
    }

    @Transactional
    public Structure updateStructure(Structure structure) {
        Optional<Structure> structureExist = structureRepository.findById(structure.getId());
        if (structureExist.isPresent()) {
            Structure structureToUpdate = structureExist.get();
            structureToUpdate.setLibelle(structure.getLibelle());
            structureRepository.save(structureToUpdate);
            return structureToUpdate;
        }
        return null;
    }

    @Transactional
    public void deleteStructure(UUID id) {
        structureRepository.deleteById(id);
    }
} 