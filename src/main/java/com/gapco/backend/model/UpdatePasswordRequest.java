package com.gapco.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    @Schema(
            name = "userId",
            description = "This is the unique Id of the user trying to change password (Email of the user)",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "userId is mandatory")
    private String userId;

    @Schema(
            name = "password",
            description = "Password of the user",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "password is mandatory")
    private String password;

    @Schema(
            name = "passwordConfirm",
            description = "Password of the user for the confirmation of the password supplied",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "password confirm is mandatory")
    private String passwordConfirm;
}
