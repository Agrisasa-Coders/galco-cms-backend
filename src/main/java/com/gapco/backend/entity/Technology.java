package com.gapco.backend.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Technology  extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String language = "english";

    @Column(columnDefinition="TEXT")
    private String description;
}
