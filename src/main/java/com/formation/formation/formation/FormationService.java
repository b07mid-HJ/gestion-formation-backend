package com.formation.formation.formation;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.formation.formation.domaine.Domaine;
import com.formation.formation.domaine.DomaineService;
import com.formation.formation.formateur.Formateur;
import com.formation.formation.formateur.FormateurService;
import com.formation.formation.participant.Participant;
import com.formation.formation.participant.ParticipantRepository;

import jakarta.transaction.Transactional;

@Component
public class FormationService {

    private final FormationRepository formationRepository;
    private final ParticipantRepository participantRepository;
    private final FormateurService formateurService;
    private final DomaineService domaineService;

    public FormationService(FormationRepository formationRepository, 
                           ParticipantRepository participantRepository,
                           FormateurService formateurService,
                           DomaineService domaineService) {
        this.formationRepository = formationRepository;
        this.participantRepository = participantRepository;
        this.formateurService = formateurService;
        this.domaineService = domaineService;
    }

    public List<Formation> getFormations() {
        return formationRepository.findAll();
    }

    public Formation getFormationById(UUID id) {
        return formationRepository.findById(id).orElseThrow(() -> new RuntimeException("Formation not found with id: " + id));
    }
    
    @Transactional
    public Formation addFormation(Formation formation) {
        // Validate required fields
        if (formation.getTitre() == null || formation.getTitre().isEmpty()) {
            throw new RuntimeException("Formation title is required");
        }
        
        if (formation.getAnnee() == null) {
            throw new RuntimeException("Formation year is required");
        }
        
        if (formation.getDuree() == null) {
            throw new RuntimeException("Formation duration is required");
        }
        
        // Verify that the formateur exists if provided
        if (formation.getFormateur() != null && formation.getFormateur().getId() != null) {
            formateurService.getFormateurById(formation.getFormateur().getId());
        }
        
        // Verify that the domaine exists if provided
        if (formation.getDomaine() != null && formation.getDomaine().getId() != null) {
            domaineService.getDomaineById(formation.getDomaine().getId());
        }
        
        formationRepository.save(formation);
        return formation;
    }
    
    @Transactional
    public Formation addFormationWithParticipantIds(FormationDTO formationDTO) {
        Formation formation = new Formation();
        formation.setTitre(formationDTO.getTitre());
        formation.setAnnee(formationDTO.getAnnee());
        formation.setDuree(formationDTO.getDuree());
        formation.setBudget(formationDTO.getBudget());
        formation.setDomaine(formationDTO.getDomaine());
        formation.setFormateur(formationDTO.getFormateur());
        
        // Save the formation first to get an ID
        formation = addFormation(formation);
        
        // Now handle the participants if any are provided
        if (formationDTO.getParticipantIds() != null && !formationDTO.getParticipantIds().isEmpty()) {
            Set<Participant> participants = formationDTO.getParticipantIds().stream()
                .map(id -> participantRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Participant not found with id: " + id)))
                .collect(Collectors.toSet());
            
            formation.setParticipants(participants);
            formationRepository.save(formation);
        }
        
        return formationRepository.findById(formation.getId()).orElse(formation);
    }

    @Transactional
    public Formation updateFormation(Formation formation) {
        Optional<Formation> formationExist = formationRepository.findById(formation.getId());
        
        if (formationExist.isPresent()) {
            Formation formationToUpdate = formationExist.get();
            
            // Validate required fields
            if (formation.getTitre() == null || formation.getTitre().isEmpty()) {
                throw new RuntimeException("Formation title is required");
            }
            
            if (formation.getAnnee() == null) {
                throw new RuntimeException("Formation year is required");
            }
            
            if (formation.getDuree() == null) {
                throw new RuntimeException("Formation duration is required");
            }
            
            // Verify that the formateur exists if it's being updated
            if (formation.getFormateur() != null && formation.getFormateur().getId() != null) {
                formateurService.getFormateurById(formation.getFormateur().getId());
            }
            
            // Verify that the domaine exists if it's being updated
            if (formation.getDomaine() != null && formation.getDomaine().getId() != null) {
                domaineService.getDomaineById(formation.getDomaine().getId());
            }
            
            formationToUpdate.setTitre(formation.getTitre());
            formationToUpdate.setAnnee(formation.getAnnee());
            formationToUpdate.setDuree(formation.getDuree());
            formationToUpdate.setBudget(formation.getBudget());
            formationToUpdate.setDomaine(formation.getDomaine());
            formationToUpdate.setFormateur(formation.getFormateur());
            formationToUpdate.setParticipants(formation.getParticipants());
            
            formationRepository.save(formationToUpdate);
            return formationToUpdate;
        }
        
        return null;
    }
    
    @Transactional
    public Formation updateFormationWithParticipantIds(FormationDTO formationDTO) {
        if (formationDTO.getId() == null) {
            throw new RuntimeException("Formation ID is required for update");
        }
        
        Formation existingFormation = getFormationById(formationDTO.getId());
        
        // Update basic fields
        existingFormation.setTitre(formationDTO.getTitre());
        existingFormation.setAnnee(formationDTO.getAnnee());
        existingFormation.setDuree(formationDTO.getDuree());
        existingFormation.setBudget(formationDTO.getBudget());
        existingFormation.setDomaine(formationDTO.getDomaine());
        existingFormation.setFormateur(formationDTO.getFormateur());
        
        // Handle participants
        if (formationDTO.getParticipantIds() != null) {
            // Get new participants from IDs
            Set<Participant> newParticipants = formationDTO.getParticipantIds().stream()
                .map(id -> participantRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Participant not found with id: " + id)))
                .collect(Collectors.toSet());
            
            existingFormation.setParticipants(newParticipants);
        } else {
            // If no participants are provided, clear the existing ones
            if (existingFormation.getParticipants() != null) {
                existingFormation.getParticipants().clear();
            }
        }
        
        return formationRepository.save(existingFormation);
    }
    
    @Transactional
    public void deleteFormation(UUID id) {
        Formation formation = getFormationById(id);
        
        // Clear participants association before deleting
        if (formation.getParticipants() != null) {
            formation.getParticipants().clear();
            formationRepository.save(formation);
        }
        
        formationRepository.delete(formation);
    }
}