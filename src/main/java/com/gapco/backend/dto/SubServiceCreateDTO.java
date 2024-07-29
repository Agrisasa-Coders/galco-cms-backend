package com.gapco.backend.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubServiceCreateDTO {

    @Schema(
            name = "serviceId",
            description = "This Id of the service",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "Long"
    )
    @NotNull(message = "serviceId should not be null")
    private Long serviceId;

    @Schema(
            name = "name",
            description = "This is the name of the sub-service",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "name is mandatory")
    private String name;

    @Schema(
            name = "description",
            description = "This is the description of the sub-service",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "name is mandatory")
    private String description;


    @Schema(
            name = "photo",
            description = "This is the picture/photo of the sub-service",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private MultipartFile photo;

    @Schema(
            name = "language",
            description = "language of the sub-service post. The default is english",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String language;
}
