package com.gapco.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Entity
public class Service  extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition="TEXT")
    private String description;

    private String photoUrl;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "service_technology", joinColumns = {
            @JoinColumn(name="service_id",referencedColumnName = "id")},inverseJoinColumns = {
            @JoinColumn(name="technology_id",referencedColumnName = "id")
    })
    private List<Technology> technologies;


    @OneToMany(mappedBy="service",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<KnowledgeBase> knowledgeBases;
}
