package com.gerardojunior.registration.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDate;

@MappedSuperclass
@Data
public class BaseEntity implements Serializable {

    @Column(nullable = false, columnDefinition = "boolean default true")
    private boolean status;

    @Column(nullable = false)
    private String createdBy;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDate createdDate;

    @Column(nullable = true)
    private String updatedBy;

    @UpdateTimestamp
    @Column(nullable = true)
    private LocalDate updatedDate;

}
