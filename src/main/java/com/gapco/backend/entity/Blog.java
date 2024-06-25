package com.gapco.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Blog extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;


    @Column(columnDefinition = "TEXT")
    private String introduction;

    private String subTitle;

    private String quote;
    private String photoUrl;

    @Column(nullable = false)
    private String language = "english";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Service service;
}
