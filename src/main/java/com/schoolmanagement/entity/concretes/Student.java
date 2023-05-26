package com.schoolmanagement.entity.concretes;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.schoolmanagement.entity.abstracts.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true,onlyExplicitlyIncluded = true)//iki objenin esit olup olmadigini hizlica bakmamizi saglar
//superdekilerle de karsilastirma imkani verir
@ToString(callSuper = true)//datayi bunun icin koymadik tostringi deafult deger verirdi
public class Student extends User {

    private String motherName;

    private String fatherName;

    private Integer studentNumber;

    private boolean isActive;

    @Column(unique = true)
    private String email;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JsonIgnore//tstringdeki recursive yapiyi engelledik
    private AdvisorTeacher advisorTeacher;
//AdviserTeacher,StudentInfo,LessonProgram,Meet

}
