package com.schoolmanagement.payload.dto;

import com.schoolmanagement.entity.concretes.Dean;
import com.schoolmanagement.payload.request.DeanRequest;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Data
//@Component  // injection yapamayiz DeanDto isminde bean bulamayiz.Cozumu configde CreateObjectBeanda
public class DeanDto {

    //dto-pojo

public Dean dtoBean(DeanRequest deanRequest){
    return Dean.builder()
            .username(deanRequest.getUsername())
            .name(deanRequest.getName())
            .surname(deanRequest.getSurname())
            .password(deanRequest.getPassword())
            .ssn(deanRequest.getSsn())
            .birthDay(deanRequest.getBirthDay())
            .birthPlace(deanRequest.getBirthPlace())
            .phoneNumber(deanRequest.getPhoneNumber())
            .gender(deanRequest.getGender())
            .build();
}






}
