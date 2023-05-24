package com.schoolmanagement.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ContactMessageResponse implements Serializable {

    //bilgi dbden gelecegi icin validationa gerek yok zaten valid yapilarak dbye gitmis

    private String name;
    private String email;
    private String message;
    private String subject;
    private LocalDate date;




}
