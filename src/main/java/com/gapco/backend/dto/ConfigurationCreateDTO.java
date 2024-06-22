package com.gapco.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConfigurationCreateDTO {

    @Schema(
            name = "maxPasswordExpiryMonths",
            description = "This is the maximum time in Months before user's password get expired. Default is 3 months",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "int"
    )
    private int maxPasswordExpiryMonths;


    @Schema(
            name = "maxPasswordAttempts",
            description = "This is maximum number of password attempts during login before account get suspended. Default is 3",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "int"
    )
    @NotNull(message = "maxPasswordAttempts is mandatory")
    private int maxPasswordAttempts;


    @Schema(
            name = "otpExpiryTimeInMinutes",
            description = "This is maximum time in minutes before OTP get expired",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "int"
    )
    private int otpExpiryTimeInMinutes;


    @Schema(
            name = "otpLength",
            description = "This is length of OTP number to be used. Default is 4",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "int"
    )
    private int otpLength;


    @Schema(
            name = "institutionSenderName",
            description = "This is the name of institution which will appear as the sender name for all notifications sent to the customer",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "institutionSenderName is mandatory")
    private String institutionSenderName;


    @Schema(
            name = "institutionId",
            description = "This is Id of the institution for this configuration",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "Long"
    )
    @NotNull(message = "institution_id is mandatory")
    private Long institutionId;
}
