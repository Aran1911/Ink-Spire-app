package com.inkSpire.application.dto.role;

import jakarta.validation.constraints.NotBlank;

public class NameUpdateRequest {
    @NotBlank(message = "Role name is required.")
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public NameUpdateRequest() {
    }

    public NameUpdateRequest(String roleName) {
        this.roleName = roleName;
    }
}
