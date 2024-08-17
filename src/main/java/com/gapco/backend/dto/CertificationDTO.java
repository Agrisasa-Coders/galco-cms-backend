package com.gapco.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CertificationDTO {
    @Schema(
            name = "name",
            description = "This is the name of the certification",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "name is mandatory")
    private String name;

    @Schema(
            name = "year",
            description = "This is the year of the certification",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "year is mandatory")
    private String year;


    @Schema(
            name = "photo",
            description = "This is the picture/photo of the certification",
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
