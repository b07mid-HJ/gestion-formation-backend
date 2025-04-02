package com.formation.formation.profil;

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
@RequestMapping(path = "api/profil")
public class ProfilController {

    private final ProfilService profilService;

    public ProfilController(ProfilService profilService){
        this.profilService=profilService;
    }

    @GetMapping()
    public List<Profil> getProfil(){
        return profilService.getProfils();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Profil> getProfilById(@PathVariable UUID id) {
        try {
            Profil profil = profilService.getProfilById(id);
            return ResponseEntity.ok(profil);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Object> createProfil(@RequestBody Profil profil) {
        try {
            Profil createdProfil = profilService.addProfil(profil);
            return new ResponseEntity<>(createdProfil, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        
    }
    
    @PutMapping
    public ResponseEntity<Profil> updateProfil( @RequestBody Profil profil) {
            Profil updatedProfil = profilService.updateProfil(profil);
            if(updatedProfil != null){
                return ResponseEntity.ok(updatedProfil);
            }else{  
                return ResponseEntity.notFound().build();
            }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfil(@PathVariable UUID id) {
        try {
            // Check if the profil exists
            profilService.getProfilById(id);
            
            profilService.deleteProfil(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
