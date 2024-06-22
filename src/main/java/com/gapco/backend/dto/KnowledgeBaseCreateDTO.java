package com.gapco.backend.dto;


import io.swagger.v3.oas.annotations.media.Schema;
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
public class KnowledgeBaseCreateDTO {
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
            name = "description",
            description = "description of the knowledgeBase",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "description is mandatory")
    private String description;
}
