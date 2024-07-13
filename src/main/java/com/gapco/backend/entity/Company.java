package com.gapco.backend.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Company  extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer institutionId;

    @Column(unique = true)
    private String name;

    private String country;

    @Column(nullable = false, columnDefinition = "integer default 1")
    private int state;


    private String city;

    private String address;


    private String email;

    private String contactPhone;

    private String contactPhoneTwo;

    private String briefHistory;
    private String vision;
    private String mission;
    private String foundedYear;

    private int wareHouses;
    private int deliveredPackages;
    private int satisfiedClients;
    private int ownedVehicles;

    private String ceoPhotoUrl;
    private String ceoFullName;
    private String ceoWord;
}
