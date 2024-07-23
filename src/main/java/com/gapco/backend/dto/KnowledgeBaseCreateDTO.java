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
            name = "subTitle",
            description = "subTitle of the blog",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "subTitle is mandatory")
    private String subTitle;


    @Schema(
            name = "quote",
            description = "quote of the blog",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    //@NotBlank(message = "quote is mandatory")
    private String quote;


    @Schema(
            name = "introduction",
            description = "introduction of the blog post",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "introduction is mandatory")
    private String introduction;


    @Schema(
            name = "photo",
            description = "This is the picture/photo of the service",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "photo is mandatory")
    private MultipartFile photo;


    @Schema(
            name = "description",
            description = "description of the knowledgeBase",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "description is mandatory")
    private String description;



    @Schema(
            name = "language",
            description = "language of the knowledgeBase post. The default is english",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String language;
}
