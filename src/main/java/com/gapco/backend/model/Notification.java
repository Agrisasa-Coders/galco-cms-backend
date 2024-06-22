package com.gapco.backend.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    private String from;

    @NotBlank(message = "destination is mandatory")
    private String to;

    @NotBlank(message = "message is mandatory")
    private String message;

    private String subject;
}
