package com.gapco.backend.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@MappedSuperclass
public class SuperEntity {

    @CreatedDate
    @JsonIgnore
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @JsonIgnore
    @Column(name = "update_at")
    private LocalDateTime updatedAt;
}
