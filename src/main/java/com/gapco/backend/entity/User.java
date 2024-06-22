package com.gapco.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false,unique = true)
    private String email;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false, columnDefinition = "integer default 0")
    @JsonIgnore
    private int noOfPasswordAttempts;

    @JsonIgnore
    private LocalDateTime passwordLastUpdate;

    @Column(nullable = false)
    private String userType;

    private String nida;
    private String region;
    private String district;

    private String membershipNo;

    private LocalDate membershipStartDate;
    private LocalDate membershipEndDate;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "role_user", joinColumns = {
            @JoinColumn(name="user_id",referencedColumnName = "id")},inverseJoinColumns = {
            @JoinColumn(name="role_id",referencedColumnName = "id")
    })
    private List<Role> roles;

    @ManyToOne
    @JoinColumn(name = "institution_id")
    private Institution institution;

    @JsonIgnore
    private String profilePicLocation;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.stream().forEach(role->authorities.add(new SimpleGrantedAuthority(role.getRoleName())));
        return  authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
