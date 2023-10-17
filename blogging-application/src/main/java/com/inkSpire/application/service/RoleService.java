package com.inkSpire.application.service;

import com.inkSpire.application.entity.Role;

import java.util.List;

public interface RoleService {
    Role saveRole(Role role);
    Role updateRole(String roleName, Role role);
    Role getRoleByName(String roleName);
    List<Role> getAllRoles();
    void init();
}
