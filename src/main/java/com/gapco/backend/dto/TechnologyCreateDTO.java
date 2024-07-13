package com.gapco.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TechnologyCreateDTO {

    @Schema(
            name = "name",
            description = "This is the name of the technology",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "name is mandatory")
    private String name;

    @Schema(
            name = "description",
            description = "This is the description of the technology",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "name is mandatory")
    private String description;


    @Schema(
            name = "photo",
            description = "This is the picture/photo of the technology",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "photo is mandatory")
    private MultipartFile photo;

    @Schema(
            name = "language",
            description = "language of the knowledgeBase post. The default is english",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String language;
}
