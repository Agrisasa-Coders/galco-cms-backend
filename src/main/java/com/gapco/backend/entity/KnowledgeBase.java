package com.gapco.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class KnowledgeBase extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String photoUrl;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    private String subTitle;

    @Column(nullable = false)
    private String language = "english";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private Service service;
}
