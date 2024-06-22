package com.gapco.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "configurations")
public class Configuration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(nullable = false,columnDefinition = "integer default 0")
    private int maxPasswordExpiryMonths;

    @Column(nullable = false,columnDefinition = "integer default 0")
    private int maxPasswordAttempts;

    @Column(nullable = false,columnDefinition = "integer default 3")
    private int otpExpiryTimeInMinutes;

    @Column(nullable = false,columnDefinition = "integer default 4")
    private int otpLength;

    private String institutionSenderName;

    @OneToOne
    @JoinColumn(name="institution_Id",referencedColumnName = "id")
    private Institution institution;
}
