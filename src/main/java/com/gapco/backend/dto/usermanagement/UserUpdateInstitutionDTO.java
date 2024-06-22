package com.gapco.backend.dto.usermanagement;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateInstitutionDTO {

    @NotNull(message = "institutions is mandatory")
    @NotEmpty(message = "institutions is empty")
    private String institutionName;
}
