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
public class TeamMemberUpdateDTO {
    @Schema(
            name = "fullName",
            description = "This is the full name of the staff",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "fullName is mandatory")
    private String fullName;

    @Schema(
            name = "photo",
            description = "This is the picture/photo of the staff",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private MultipartFile photo;

    @Schema(
            name = "position",
            description = "This is the position of the staff",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotEmpty(message = "position is mandatory")
    private String position;


    @Schema(
            name = "language",
            description = "language for the post. The default is english",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String language;
}
