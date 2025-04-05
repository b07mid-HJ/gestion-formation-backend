package com.formation.formation.participant;

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
@RequestMapping(path = "api/participant")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping()
    public List<Participant> getParticipants() {
        return participantService.getParticipants();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Participant> getParticipantById(@PathVariable UUID id) {
        try {
            Participant participant = participantService.getParticipantById(id);
            return ResponseEntity.ok(participant);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Object> createParticipant(@RequestBody ParticipantDTO participantDTO) {
        try {
            Participant createdParticipant = participantService.addParticipantWithFormationIds(participantDTO);
            return new ResponseEntity<>(createdParticipant, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PutMapping
    public ResponseEntity<Object> updateParticipant(@RequestBody ParticipantDTO participantDTO) {
        try {
            Participant updatedParticipant = participantService.updateParticipantWithFormationIds(participantDTO);
            if (updatedParticipant != null) {
                return ResponseEntity.ok(updatedParticipant);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Participant not found");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable UUID id) {
        try {
            // Check if the participant exists
            participantService.getParticipantById(id);
            
            participantService.deleteParticipant(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 