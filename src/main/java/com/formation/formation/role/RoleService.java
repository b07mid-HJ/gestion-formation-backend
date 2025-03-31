package com.formation.formation.role;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;

@Component
public class RoleService {

    private final RoleRepository roleRepository;
    private static final Set<String> ALLOWED_ROLE_NAMES = new HashSet<>(Arrays.asList("simple", "responsable", "admin"));

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleById(UUID id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
    }

    public Role addRole(Role role) {
        validateRoleName(role.getNom());
        roleRepository.save(role);
        return role;
    }

    @Transactional
    public Role updateRole(Role role) {
        validateRoleName(role.getNom());
        
        Optional<Role> roleExist = roleRepository.findById(role.getId());
        if (roleExist.isPresent()) {
            Role roleToUpdate = roleExist.get();
            roleToUpdate.setNom(role.getNom());
            roleRepository.save(roleToUpdate);
            return roleToUpdate;
        }
        return null;
    }

    @Transactional
    public void deleteRole(UUID id) {
        roleRepository.deleteById(id);
    }
    
    private void validateRoleName(String roleName) {
        if (roleName == null || !ALLOWED_ROLE_NAMES.contains(roleName.toLowerCase())) {
            throw new IllegalArgumentException("Role name must be one of: simple, responsable, admin");
        }
    }
} 