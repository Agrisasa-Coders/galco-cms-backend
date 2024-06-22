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
public class SendOTPRequest {

    @Schema(
            name = "username",
            description = "This is identification of the user to send OTP to, it can be either email or phone number of the user",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "username is mandatory")
    private String username;

    @Schema(
            name = "channel",
            description = "This is the channel which is used to send OTP to the user, OTP can be sent through either email for phone",
            requiredMode = Schema.RequiredMode.REQUIRED,
            allowableValues = "email,phone",
            type = "String"
    )
    @NotBlank(message = "channel is mandatory")
    private String channel;
}
