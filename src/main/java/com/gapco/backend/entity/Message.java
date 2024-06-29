package com.gapco.backend.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "messages")
public class Message extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(
            name = "fullName",
            description = "fullName of the customer who has sent the message",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "fullName is mandatory")
    private String fullName;

    @Schema(
            name = "phoneNumber",
            description = "phoneNumber of the customer who has sent the message",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "phoneNumber is mandatory")
    private String phoneNumber;


    @Schema(
            name = "email",
            description = "email of the customer who has sent the message",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "email is mandatory")
    private String email;


    @Schema(
            name = "city",
            description = "city of the customer who has sent the message",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "city is mandatory")
    private String city;


    @Schema(
            name = "message",
            description = "message from the customer",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "message is mandatory")
    @Column(columnDefinition = "TEXT")
    private String message;
}
