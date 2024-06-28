package com.gapco.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CustomerCreateDTO {

    @Schema(
            name = "fullName",
            description = "fullName of the customer",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "fullName is mandatory")
    private String fullName;


    @Schema(
            name = "companyName",
            description = "companyName of the customer",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "companyName is mandatory")
    private String companyName;


    @Schema(
            name = "comments",
            description = "comments of the customer about the company",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "comments is mandatory")
    private String comments;

    @Schema(
            name = "rating",
            description = "Rating of the customer to the company",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "int"
    )
    @NotNull(message = "rating")
    private Integer rating;

    @Schema(
            name = "photo",
            description = "This is the picture/photo of the customer",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private MultipartFile photo;

    @Schema(
            name = "language",
            description = "language of the customer's comments. The default is english",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String language;
}
