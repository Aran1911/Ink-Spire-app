package com.inkSpire.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.Objects;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleID;

    @NotBlank(message = "Role name is required.")
    @Column(unique = true)
    private String roleName;

    @Column(columnDefinition = "TEXT")
    @NotBlank(message = "Role description is required.")
    private String roleDescription;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName.toUpperCase();
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Long getRoleID() {
        return roleID;
    }

    public void setRoleID(Long roleID) {
        this.roleID = roleID;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Role role)) return false;
        return Objects.equals(getRoleID(), role.getRoleID()) && Objects.equals(getRoleName(), role.getRoleName()) && Objects.equals(getRoleDescription(), role.getRoleDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleID(), getRoleName(), getRoleDescription());
    }

    public Role() {
    }

    public Role(Long roleID, String roleName, String roleDescription) {
        this.roleID = roleID;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    public Role(String roleName, String roleDescription) {
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }
}
