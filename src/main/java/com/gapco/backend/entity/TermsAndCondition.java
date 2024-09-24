package com.gapco.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TermsAndCondition extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String terms;
}
