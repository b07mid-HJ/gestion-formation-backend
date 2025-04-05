package com.formation.formation.participant;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.formation.formation.formation.Formation;
import com.formation.formation.formation.FormationRepository;
import com.formation.formation.profil.ProfilService;
import com.formation.formation.structure.StructureService;

import jakarta.transaction.Transactional;

@Component
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final StructureService structureService;
    private final ProfilService profilService;
    private final FormationRepository formationRepository;

    public ParticipantService(ParticipantRepository participantRepository, 
                             StructureService structureService,
                             ProfilService profilService,
                            FormationRepository formationRepository) {
        this.participantRepository = participantRepository;
        this.structureService = structureService;
        this.profilService = profilService;
        this.formationRepository = formationRepository;
    }

    public List<Participant> getParticipants() {
        return participantRepository.findAll();
    }

    public Participant getParticipantById(UUID id) {
        return participantRepository.findById(id).orElseThrow(() -> new RuntimeException("Participant not found with id: " + id));
    }
    
    @Transactional
    public Participant addParticipant(Participant participant) {
        // Check if participant with same email, nom and prenom already exists
        Optional<Participant> existingParticipant = participantRepository.findByEmailAndNomAndPrenom(
                participant.getEmail(), participant.getNom(), participant.getPrenom());
        
        if (existingParticipant.isPresent()) {
            throw new RuntimeException("A participant with this email, name and surname already exists");
        }
        
        // Verify that the structure exists if provided
        if (participant.getStructure() != null && participant.getStructure().getId() != null) {
            structureService.getStructureById(participant.getStructure().getId());
        }
        
        // Verify that the profil exists if provided
        if (participant.getProfil() != null && participant.getProfil().getId() != null) {
            profilService.getProfilById(participant.getProfil().getId());
        }
        
        participantRepository.save(participant);
        return participant;
    }

    @Transactional
    public Participant addParticipantWithFormationIds(ParticipantDTO participantDTO) {
        Participant participant = new Participant();
        participant.setNom(participantDTO.getNom());
        participant.setPrenom(participantDTO.getPrenom());
        participant.setEmail(participantDTO.getEmail());
        participant.setTel(participantDTO.getTel());
        participant.setStructure(participantDTO.getStructure());
        participant.setProfil(participantDTO.getProfil());
        
        // Save the participant first to get an ID
        participant = addParticipant(participant);
        
        // Now handle the formations if any are provided
        if (participantDTO.getFormationIds() != null && !participantDTO.getFormationIds().isEmpty()) {
            Set<Formation> formations = participantDTO.getFormationIds().stream()
                .map(id -> formationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Formation not found with id: " + id)))
                .collect(Collectors.toSet());
            
            // Add participant to each formation
            for (Formation formation : formations) {
                if (formation.getParticipants() == null) {
                    formation.setParticipants(new HashSet<>());
                }
                formation.getParticipants().add(participant);
                formationRepository.save(formation);
            }
        }
        
        return participantRepository.findById(participant.getId()).orElse(participant);
    }

    @Transactional
    public Participant updateParticipant(Participant participant) {
        Optional<Participant> participantExist = participantRepository.findById(participant.getId());
        
        if (participantExist.isPresent()) {
            Participant participantToUpdate = participantExist.get();
            
            // Check if another participant with the new email, nom and prenom already exists
            if (!participantToUpdate.getEmail().equals(participant.getEmail()) || 
                !participantToUpdate.getNom().equals(participant.getNom()) || 
                !participantToUpdate.getPrenom().equals(participant.getPrenom())) {
                
                Optional<Participant> duplicateParticipant = participantRepository.findByEmailAndNomAndPrenom(
                        participant.getEmail(), participant.getNom(), participant.getPrenom());
                
                if (duplicateParticipant.isPresent() && !duplicateParticipant.get().getId().equals(participant.getId())) {
                    throw new RuntimeException("Another participant with this email, name and surname already exists");
                }
            }
            
            // Verify that the structure exists if it's being updated
            if (participant.getStructure() != null && participant.getStructure().getId() != null) {
                structureService.getStructureById(participant.getStructure().getId());
            }
            
            // Verify that the profil exists if it's being updated
            if (participant.getProfil() != null && participant.getProfil().getId() != null) {
                profilService.getProfilById(participant.getProfil().getId());
            }
            
            participantToUpdate.setNom(participant.getNom());
            participantToUpdate.setPrenom(participant.getPrenom());
            participantToUpdate.setEmail(participant.getEmail());
            participantToUpdate.setTel(participant.getTel());
            participantToUpdate.setStructure(participant.getStructure());
            participantToUpdate.setProfil(participant.getProfil());
            
            participantRepository.save(participantToUpdate);
            return participantToUpdate;
        }
        
        return null;
    }

    @Transactional
    public Participant updateParticipantWithFormationIds(ParticipantDTO participantDTO) {
        if (participantDTO.getId() == null) {
            throw new RuntimeException("Participant ID is required for update");
        }
        
        Participant existingParticipant = getParticipantById(participantDTO.getId());
        
        // Update basic fields
        existingParticipant.setNom(participantDTO.getNom());
        existingParticipant.setPrenom(participantDTO.getPrenom());
        existingParticipant.setEmail(participantDTO.getEmail());
        existingParticipant.setTel(participantDTO.getTel());
        existingParticipant.setStructure(participantDTO.getStructure());
        existingParticipant.setProfil(participantDTO.getProfil());
        
        // Handle formations
        if (participantDTO.getFormationIds() != null && !participantDTO.getFormationIds().isEmpty()) {
            // Get current formations
            Set<Formation> currentFormations = existingParticipant.getFormations();
            
            // Get new formations from IDs
            Set<Formation> newFormations = participantDTO.getFormationIds().stream()
                .map(id -> formationRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Formation not found with id: " + id)))
                .collect(Collectors.toSet());
            
            // Remove participant from formations that are no longer associated
            if (currentFormations != null) {
                for (Formation formation : currentFormations) {
                    if (!newFormations.contains(formation)) {
                        formation.getParticipants().remove(existingParticipant);
                        formationRepository.save(formation);
                    }
                }
            }
            
            // Add participant to new formations
            for (Formation formation : newFormations) {
                if (formation.getParticipants() == null) {
                    formation.setParticipants(new HashSet<>());
                }
                if (!formation.getParticipants().contains(existingParticipant)) {
                    formation.getParticipants().add(existingParticipant);
                    formationRepository.save(formation);
                }
            }
        }
        
        return participantRepository.save(existingParticipant);
    }
    
    @Transactional
    public void deleteParticipant(UUID id) {
        Participant participant = getParticipantById(id);
        
        // Remove participant from all formations
        if (participant.getFormations() != null) {
            for (Formation formation : participant.getFormations()) {
                formation.getParticipants().remove(participant);
            }
            participant.getFormations().clear();
        }
        
        participantRepository.delete(participant);
    }
} 