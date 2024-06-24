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
public class KnowledgeBaseUpdateDTO {
    @Schema(
            name = "serviceId",
            description = "This Id of the service",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "Long"
    )
    @NotNull(message = "serviceId should not be null")
    private Long serviceId;


    @Schema(
            name = "title",
            description = "title of the institution",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "title is mandatory")
    private String title;


    @Schema(
            name = "photo",
            description = "This is the picture/photo of the service",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private MultipartFile photo;


    @Schema(
            name = "description",
            description = "description of the knowledgeBase",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "description is mandatory")
    private String description;
}
