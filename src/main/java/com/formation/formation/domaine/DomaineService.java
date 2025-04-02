package com.formation.formation.domaine;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class DomaineService {

    private final DomaineRepository domaineRepository;

    public DomaineService(DomaineRepository domaineRepository) {
        this.domaineRepository = domaineRepository;
    }

    public List<Domaine> getDomaines() {
        return domaineRepository.findAll();
    }

    public Domaine getDomaineById(UUID id) {
        return domaineRepository.findById(id).orElseThrow(() -> new RuntimeException("Domaine not found with id: " + id));
    }

    public Domaine addDomaine(Domaine domaine) {
        // check if the domaine already exists
        Optional<Domaine> existingDomaine = domaineRepository.findByLibelle(domaine.getLibelle());
        if (existingDomaine.isPresent()) {
            throw new RuntimeException("A domain with this name already exists");
        }
        domaineRepository.save(domaine);
        return domaine;
    }

    @Transactional
    public Domaine updateDomaine(Domaine domaine) {
        Optional<Domaine> domaineExist = domaineRepository.findById(domaine.getId());
        if (domaineExist.isPresent()) {
            Domaine domaineToUpdate = domaineExist.get();
            domaineToUpdate.setLibelle(domaine.getLibelle());
            domaineRepository.save(domaineToUpdate);
            return domaineToUpdate;
        }
        return null;
    }

    @Transactional
    public void deleteDomaine(UUID id) {
        domaineRepository.deleteById(id);
    }
} 