package com.formation.formation.profil;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class ProfilService {

    private final ProfilRepository profilRepository;

    public ProfilService(ProfilRepository ProfilRepository){
        this.profilRepository=ProfilRepository;
    }

    public List<Profil> getProfils(){
        return profilRepository.findAll();
    }

    public Profil getProfilById(UUID id) {
        return profilRepository.findById(id).orElseThrow(()->new RuntimeException("Profil not found with id: " + id));
    }

    public Profil addProfil(Profil profil){
        profilRepository.save(profil);
        return profil;
    }

    @Transactional
    public void deleteProfil(UUID id){
        profilRepository.deleteById(id);

    }
}

