package com.formation.formation.profil;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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

}
