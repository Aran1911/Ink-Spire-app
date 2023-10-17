package com.inkSpire.application.dto.role;

import jakarta.validation.constraints.NotBlank;

public class DescriptionUpdateRequest {
    @NotBlank(message = "Role description is required.")
    private String roleDescription;

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public DescriptionUpdateRequest() {
    }

    public DescriptionUpdateRequest(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
