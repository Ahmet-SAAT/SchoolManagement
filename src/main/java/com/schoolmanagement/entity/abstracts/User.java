package com.schoolmanagement.entity.abstracts;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.schoolmanagement.entity.concretes.UserRole;
import com.schoolmanagement.entity.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass//DB!de user tablosu olusmadan bu clasin anac sinif olarak kullanilmasini sagliyor.
//bazi entityler bu classdan tureceyecek diyorum
@SuperBuilder//entity alan alt siniflar icin de builder demis olduk
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

     @Column(unique = true)
     private String username;

     @Column(unique = true)
     private String ssn;

     private String name;

     private String surname;

     @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd")
     private LocalDate birthDay;

     private String birthPlace;

     @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)//write clienten db ye giderken demek
     // yani sadece yazarken password gorulecek okuma isleminde kullanilmayacak.
     private String password;

     @Column(unique = true)
     private String phoneNumber;

     @OneToOne
     @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
     private UserRole userRole;

     private Gender gender;









}
