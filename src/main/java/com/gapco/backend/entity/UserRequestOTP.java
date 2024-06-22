package com.gapco.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_request_otps")
public class UserRequestOTP {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank(message = "userId is mandatory")
    private String userId;

    @NotBlank(message = "otp is mandatory")
    private String otp;

    private boolean isValidated = false;

    private LocalDateTime expiredAt;
}
