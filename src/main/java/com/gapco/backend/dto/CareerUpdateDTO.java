package com.gapco.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CareerUpdateDTO {
    @Schema(
            name = "title",
            description = "title of the career",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "title is mandatory")
    private String title;


    @Schema(
            name = "requirements",
            description = "requirements of the career opportunity",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "requirements is mandatory")
    private String requirements;


    @Schema(
            name = "photo",
            description = "This is the picture/photo of the service",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private MultipartFile photo;

    @Schema(
            name = "document",
            description = "This is the document explaining the career",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private MultipartFile document;


    @Schema(
            name = "shortDescription",
            description = "shortDescription of the career",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "shortDescription is mandatory")
    private String shortDescription;


    @Schema(
            name = "language",
            description = "language of the blog post. The default is english",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String language;

    @Schema(
            name = "jobPostUrl",
            description = "This is Url of the job posted",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String jobPostUrl;
}
