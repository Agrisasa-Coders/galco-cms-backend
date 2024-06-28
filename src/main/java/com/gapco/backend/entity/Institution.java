package com.gapco.backend.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class Institution extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer institutionId;

    @Column(unique = true)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "country is mandatory")
    private String country;

    @Column(nullable = false, columnDefinition = "integer default 1")
    private int state;

    @NotBlank(message = "city is mandatory")
    private String city;

    @NotBlank(message = "address is mandatory")
    private String address;

    @NotBlank(message = "email is mandatory")
    private String email;

    @NotBlank(message = "contactPhone is mandatory")
    private String contactPhone;

    @NotBlank(message = "contactPhoneTwo is mandatory")
    private String contactPhoneTwo;

    private String briefHistory;
    private String vision;
    private String mission;
    private String foundedYear;

    private int wareHouses;
    private int deliveredPackages;
    private int satisfiedClients;
    private int ownedVehicles;
}
