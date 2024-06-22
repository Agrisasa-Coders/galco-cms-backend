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
public class ValidateOTPRequest {

    @Schema(
            name = "username",
            description = "This is identification of the user,it can be either email or phone number of the user",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "username is mandatory")
    private String username;

    @Schema(
            name = "otp",
            description = "OTP to validate",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "otp is mandatory")
    private String otp;
}
