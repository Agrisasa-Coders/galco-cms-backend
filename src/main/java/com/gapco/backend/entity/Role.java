package com.gapco.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false,unique = true)
    private String roleName;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name="role_permissions",
            joinColumns = {
                    @JoinColumn(name = "role_id",referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "permission_id",referencedColumnName = "id")
            }
    )
    private List<Permission> permissions;

}
