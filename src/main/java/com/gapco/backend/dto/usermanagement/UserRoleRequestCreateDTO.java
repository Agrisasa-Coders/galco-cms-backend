package com.gapco.backend.dto.usermanagement;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleRequestCreateDTO {

    @NotBlank(message = "firstName is mandatory")
    private String firstName;

    @NotBlank(message = "middleName is mandatory")
    private String middleName;

    @NotBlank(message = "lastName is mandatory")
    private String lastName;

    @NotBlank(message = "email is mandatory")
    @Column(nullable = false,unique = true)
    private String email;

    @NotNull(message = "institutionId is mandatory")
    private Long institutionId;

    @NotNull(message = "agentId is mandatory")
    private Long agentId;

    @NotNull(message = "roleId is mandatory")
    private Long roleId;

    @NotNull(message = "collectionPointId is mandatory")
    private Long collectionPointId;

    @NotBlank(message = "phoneNumber is mandatory")
    private String phoneNumber;

    private String DateOfBirth;

    private String latitude;

    private String longitude;
}
