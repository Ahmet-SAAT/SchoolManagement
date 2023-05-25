package com.schoolmanagement.entity.concretes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Student {

    private String motherName;

    private String fatherName;

    private Integer studentNumber;

    private boolean isActive;

    @Column(unique = true)
    private String email;



}
