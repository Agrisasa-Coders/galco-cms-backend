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
    private String introduction;

    @Column(columnDefinition="TEXT")
    private String description;

    private String subServiceHeader ;

    private String subServiceTagline;

    private String photoUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "service_technology", joinColumns = {
            @JoinColumn(name="service_id",referencedColumnName = "id")},inverseJoinColumns = {
            @JoinColumn(name="technology_id",referencedColumnName = "id")
    })
    private List<Technology> technologies;


    @OneToMany(mappedBy="service",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private Set<KnowledgeBase> knowledgeBases;

    @OneToMany(mappedBy="service",cascade = CascadeType.ALL,orphanRemoval = true)
   // @JsonIgnore
    private Set<SubService> subServices;

    @OneToMany(mappedBy="service",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private Set<Blog> blogs;


    @OneToMany(mappedBy="service",cascade = CascadeType.ALL,orphanRemoval = true)
//    @JsonIgnore
    private List<Gallery> photos;
}
