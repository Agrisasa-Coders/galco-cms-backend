package com.gapco.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Certification extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    //    @Column(columnDefinition="TEXT")
    private String year;

    private String photoUrl;
}