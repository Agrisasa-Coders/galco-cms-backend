package com.gapco.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GalleryCreateDTO {
    @Schema(
            name = "serviceId",
            description = "This Id of the service",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "Long"
    )
    @NotNull(message = "serviceId should not be null")
    private Long serviceId;

    @Schema(
            name = "photos",
            description = "This is the photos of the service",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @NotNull(message = "photo is mandatory")
    @NotEmpty(message = "photos should not be empty")
    private MultipartFile[] photos;
}
