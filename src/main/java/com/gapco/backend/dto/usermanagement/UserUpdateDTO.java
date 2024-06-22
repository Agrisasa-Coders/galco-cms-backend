package com.gapco.backend.dto.usermanagement;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserUpdateDTO {
    @NotBlank(message = "first name is mandatory")
    private String firstName;

    @NotBlank(message = "last name is mandatory")
    private String lastName;
    
    @NotBlank(message = "email name is mandatory")
    @Email(message = "email must be valid")
    private String email;

    @NotBlank(message = "phone number is mandatory")
    private String phoneNumber;

    private MultipartFile multipartImage;
}
