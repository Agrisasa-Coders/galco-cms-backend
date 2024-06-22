package com.gapco.backend.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition="TEXT")
    private String description;
}
