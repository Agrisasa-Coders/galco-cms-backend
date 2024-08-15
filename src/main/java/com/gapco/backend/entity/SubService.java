package com.gapco.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SubService extends Auditable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition="TEXT")
    private String description;

    private String photoUrl;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private Service service;
}
