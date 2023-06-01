package com.schoolmanagement.payload.response.abstracts;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.schoolmanagement.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@SuperBuilder
//@MappedSuperclass//tablo olusturmadan anac class yaptik.Ama dbdeb cliente gidecek buna gerek yok
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserResponse implements Serializable {


    private String username;

    private Long userId;


    private String name;


    private String surname;


    private LocalDate birthDay;


    private String ssn;


    private String birthPlace;


    private String password;


    private String phoneNumber;


    private Gender gender;
}
