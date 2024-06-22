package com.gapco.backend.dto.usermanagement;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PermissionsCreateDTO {

    @NotNull(message = "permissions field is mandatory")
    private String[] permissions;
}

