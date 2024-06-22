package com.gapco.backend.dto.usermanagement;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TemporalUserCreateDTO {

    @NotBlank(message = "firstName is mandatory")
    private String firstName;

    @NotBlank(message = "middleName is mandatory")
    private String middleName;

    @NotBlank(message = "lastName is mandatory")
    private String lastName;

    @NotBlank(message = "email is mandatory")
    @Column(nullable = false,unique = true)
    private String email;

    //@NotBlank(message = "phoneNumber is mandatory")
    private String phoneNumber;

    private String DateOfBirth;

    private String latitude;

    private String longitude;

}
