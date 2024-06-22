package com.gapco.backend.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    @Schema(
            name = "email",
            description = "Email of the user",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "email name is mandatory")
    @Email(message = "email must be valid")
    private String email;



    @Schema(
            name = "password",
            description = "Password of the user",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "Varchar"
    )
    @NotBlank(message = "password is mandatory")
    private String password;
}
