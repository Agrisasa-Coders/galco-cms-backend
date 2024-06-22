package com.gapco.backend.dto.usermanagement;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterDTO {

    @Schema(
            name = "firstName",
            description = "First name for the user",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "first name is mandatory")
    private String firstName;


    @Schema(
            name = "middleName",
            description = "Middle Name of the user",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String middleName;


    @Schema(
            name = "lastName",
            description = "Last name of the user",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "last name is mandatory")
    private String lastName;

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
            name = "phoneNumber",
            description = "Phone number of the user",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "phone number is mandatory")
    private String phoneNumber;


    @Schema(
            name = "password",
            description = "Password of the user",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "Varchar"
    )
    @NotBlank(message = "password is mandatory")
    private String password;


    @Schema(
            name = "passwordConfirm",
            description = "Password confirm of the user to match passwords supplied",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "Varchar"
    )
    @NotBlank(message = "confirm password is mandatory")
    private String passwordConfirm;

    @Schema(
            name = "roles",
            description = "List of roles for the user",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "Array"
    )
    private Long[] roles;

    @Schema(
            name = "userType",
            description = "used to determine different types of users in the system",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "Varchar"
    )
    private String userType;

    @Schema(
            name = "region",
            description = "Region of the user",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "Varchar"
    )
    private String region;


    @Schema(
            name = "district",
            description = "District of the user",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "Varchar"
    )
    private String district;


    @Schema(
            name = "nida",
            description = "NIDA registration number  of the user",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "Varchar"
    )
    private String nida;


    @Schema(
            name = "membershipNo",
            description = "Membership number of the user who qualified to be a member of an application",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "Varchar"
    )
    private String membershipNo;


    @Schema(
            name = "institutionId",
            description = "This is Id of the institution for this configuration",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "Long"
    )
    private Long institutionId;
}
