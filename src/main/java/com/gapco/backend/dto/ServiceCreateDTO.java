package com.gapco.backend.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceCreateDTO {

    @Schema(
            name = "name",
            description = "This is the name of the service",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "name is mandatory")
    private String name;

    @Schema(
            name = "photo",
            description = "This is the picture/photo of the service",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "photo is mandatory")
    private MultipartFile photo;

    @Schema(
            name = "description",
            description = "This is the description of the service",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotEmpty(message = "description is mandatory")
    private String description;

    @Schema(
            name = "technologies",
            description = "List",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "Array"
    )
    private Long[] technologies;

    @Schema(
            name = "language",
            description = "language for the service post. The default is english",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String language;
}
