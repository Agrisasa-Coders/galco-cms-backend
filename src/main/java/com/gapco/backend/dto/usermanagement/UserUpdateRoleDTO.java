package com.gapco.backend.dto.usermanagement;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateRoleDTO {

    @NotNull(message = "roles is null")
    private Long[] roles;
}
