package com.inkSpire.application.service.impl;

import com.inkSpire.application.entity.Role;
import com.inkSpire.application.exception.RoleAlreadyExistsException;
import com.inkSpire.application.exception.RoleNotFoundException;
import com.inkSpire.application.repository.RoleRepository;
import com.inkSpire.application.service.RoleService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepo;
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Override
    public Role saveRole(@NotNull @Valid Role role) {
        if ((roleRepo.findByRoleName(role.getRoleName())).isPresent()) {
            LOGGER.error("There is a role already exist with name {}", role.getRoleName());
            throw new RoleAlreadyExistsException("There is a role already exist with name.");
        } else {
            return roleRepo.save(role);
        }
    }

    @Transactional
    @Override
    public Role updateRole(String roleName, Role role) {
        Role existingRole = roleRepo.findByRoleName(roleName).orElseThrow(() -> {
            LOGGER.error("An error occurred while retrieving the role details.");
            return new RoleNotFoundException("There is no role with this role name.");
        });

        existingRole.setRoleName(role.getRoleName());
        existingRole.setRoleDescription(role.getRoleDescription());
        return roleRepo.save(existingRole);

    }

    @Override
    public Role getRoleByName(String roleName) {
        return roleRepo.findByRoleName(roleName).orElseThrow(() -> {
            LOGGER.error("An error occurred while retrieving the role details.");
            return new RoleNotFoundException("There is no role with this role.");
        });
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepo.findAll();
    }

    @Override
    public void init() {
        Role adminRole = new Role("ADMIN", "Administrator of this app.");
        Role userRole = new Role("USER", "User of this app.");
        roleRepo.saveAll(List.of(adminRole, userRole));
    }
}
