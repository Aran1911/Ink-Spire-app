package com.inkSpire.application.controller;

import com.inkSpire.application.entity.Role;
import com.inkSpire.application.service.impl.RoleServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class RoleController {

    private final RoleServiceImpl roleService;

    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Role> createNewRole(@RequestBody Role role) {
        return new ResponseEntity<>(roleService.saveRole(role), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Role> updateRoleDetails(@RequestParam(name = "roleName") String roleName, @RequestBody Role role) {
        return new ResponseEntity<>(roleService.updateRole(roleName, role), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(roleService.getAllRoles(), HttpStatus.OK);
    }

    @PostConstruct
    public void initRoles(){
        roleService.init();
    }
}
