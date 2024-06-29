package com.gapco.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InstitutionCreateDTO {

    @Schema(
            name = "institutionId",
            description = "This Id of the institution which is generated to differentiate super institution and others. Only required when creating super institution",
            requiredMode = Schema.RequiredMode.AUTO,
            type = "int"
    )
    private Integer institutionId;


    @Schema(
            name = "name",
            description = "Name of the institution",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "Name is mandatory")
    private String name;


    @Schema(
            name = "country",
            description = "Country of the institution",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "country is mandatory")
    private String country;


    @Schema(
            name = "city",
            description = "City of the institution",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "city is mandatory")
    private String city;


    @Schema(
            name = "address",
            description = "Address of the institution",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "address is mandatory")
    private String address;


    @Schema(
            name = "email",
            description = "contact email of the institution",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "email is mandatory")
    private String email;


    @Schema(
            name = "institutionType",
            description = "Type of the institution",
//            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
//    @NotBlank(message = "institutionType is mandatory")
    private String institutionType;


    @Schema(
            name = "contactPhone",
            description = "First contact person of the institution",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "contactPhone is mandatory")
    private String contactPhone;

    @Schema(
            name = "foundedYear",
            description = "Founded year of the institution",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String foundedYear;

    @Schema(
            name = "briefHistory",
            description = "Brief history of the institution",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String briefHistory;


    @Schema(
            name = "vision",
            description = "Vision of the institution",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String vision;


    @Schema(
            name = "mission",
            description = "Mission of the institution",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String mission;


    @Schema(
            name = "contactPhoneTwo",
            description = "Second contact person of the institution",
            requiredMode = Schema.RequiredMode.REQUIRED,
            type = "String"
    )
    @NotBlank(message = "contactPhoneTwo is mandatory")
    private String contactPhoneTwo;


    @Schema(
            name = "language",
            description = "language of the post. The default is english",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String language;

    @Schema(
            name = "wareHouses",
            description = "Number of company's warehouses",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "int"
    )
    private Integer wareHouses;

    @Schema(
            name = "wareHouses",
            description = "Total of company's delivered packages",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "int"
    )
    private Integer deliveredPackages;

    @Schema(
            name = "wareHouses",
            description = "Number of company's satisfied clients",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "int"
    )
    private Integer satisfiedClients;


    @Schema(
            name = "wareHouses",
            description = "Number of company's owned vehicles",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "int"
    )
    private Integer ownedVehicles;


    @Schema(
            name = "ceoWord",
            description = "Word from the Company's CEO",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String ceoWord;


    @Schema(
            name = "ceoFullName",
            description = "FullName of the company's CEO",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED,
            type = "String"
    )
    private String ceoFullName;

    @Schema(
            name = "ceoPhoto",
            description = "This is the picture/photo of the company's CEO",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED
    )
    private MultipartFile ceoPhoto;
}
