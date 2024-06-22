package com.gapco.backend.dto.usermanagement;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleCreateDTO {


    @Schema(
            name = "roleName",
            description = "Name of the role",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "roleName is mandatory")
    private String roleName;



    @Schema(
            name = "permissions",
            description = "List of permissions to assign to the role",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "Array"
    )
    @NotNull(message = "permissions is null")
    @NotEmpty(message = "permissions is empty")
    private Long[] permissions;

}
