package com.schoolmanagement.payload.request.abstracts;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.schoolmanagement.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.annotation.security.DenyAll;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@SuperBuilder
@MappedSuperclass//tablo olusturmadan anac class yaptik
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseUserRequest implements Serializable {

    @NotNull(message = "Please enter your username")
    @Size(min=4, max=16, message = "Your username should be at least 4 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your username must consist of the characters .")
    private String username;

    @NotNull(message = "Please enter your username")
    @Size(min=2, max=16, message = "Your name should be at least 2 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your username must consist of the characters .")
    private String name;

    @NotNull(message = "Please enter your username")
    @Size(min=2, max=16, message = "Your surname should be at least 2 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your username must consist of the characters .")
    private String surname;

    @NotNull(message = "Please enter your birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM=dd")
    @Past//bu tarih kontrolu yapiyor.
    private LocalDate birthDay;

    @NotNull(message = "Plase enter your ssn")
    @Pattern(regexp = "^(?!000|666)[0-8][0-9]{2}-(?!00)[0-9]{2}-(?!0000)[0-9]{4}$",
            message = "Please enter valid SSN number")
    private String ssn;

    @NotNull(message = "Plase enter your birth place")
    @Size(min=2, max=16, message = "Your birth place should be at least 2 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+", message = "Your birth place must consist of the characters .")
    private String birthPlace;

    @NotNull(message = "Plase enter your password")
    @Size(min=8, max=60, message = "Your password should be at least 8 chars")
    //@Column(nullable = false,length = 60)
    private String password;

    @NotNull(message = "Plase enter your phoneNumber")
    @Size(min = 12, max = 12,message = "Phone number should be 12 chars")
    private String phoneNumber;

    @NotNull(message = "Please enter your gender")
    private Gender gender;
}
