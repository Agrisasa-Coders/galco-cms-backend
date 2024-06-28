package com.gapco.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Customer extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String companyName;

    @Column(columnDefinition = "TEXT")
    private String comments;
    private String photoUrl;
    private int rating;
}
