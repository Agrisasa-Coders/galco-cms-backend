package com.gapco.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Team extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String position;
    private String photoUrl;

    @Column(nullable = false)
    private String language = "english";
}
