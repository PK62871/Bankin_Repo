package com.eazybytes.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@MappedSuperclass
@Data
public class BaseEntity {


    @Column(updatable = false)
    private LocalDateTime createdAt;
    private String createBy;

@Column(insertable = false)
    private LocalDateTime updatedAt;
    private String updatedBy;
}
