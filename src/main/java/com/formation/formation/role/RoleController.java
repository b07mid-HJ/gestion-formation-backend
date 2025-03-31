package com.formation.formation.role;

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
@RequestMapping(path = "api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping()
    public List<Role> getRoles() {
        return roleService.getRoles();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable UUID id) {
        try {
            Role role = roleService.getRoleById(id);
            return ResponseEntity.ok(role);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
            Role createdRole = roleService.addRole(role);
            return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }
    
    @PutMapping
    public ResponseEntity<Role> updateRole(@RequestBody Role role) {
        
            Role updatedRole = roleService.updateRole(role);
            if (updatedRole != null) {
                return ResponseEntity.ok(updatedRole);
            } else {
                return ResponseEntity.notFound().build();
            }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable UUID id) {
        try {
            // Check if the role exists
            roleService.getRoleById(id);
            
            roleService.deleteRole(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 