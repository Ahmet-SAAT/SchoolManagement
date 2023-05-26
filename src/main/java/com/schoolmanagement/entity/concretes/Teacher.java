package com.schoolmanagement.entity.concretes;

import com.schoolmanagement.entity.abstracts.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(callSuper = true)
public class Teacher extends User {

    @OneToOne(mappedBy = "teacher",cascade = CascadeType.PERSIST,orphanRemoval = true)
    private AdvisorTeacher advisorTeacher;

    @Column(name = "isAdvisor")
    private Boolean isAdvisor;

    @Column(unique = true)
    private String email;

    //!!!StudentInfo,

}