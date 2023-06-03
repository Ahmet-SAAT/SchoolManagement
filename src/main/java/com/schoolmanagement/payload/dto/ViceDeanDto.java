package com.schoolmanagement.payload.dto;

import com.schoolmanagement.entity.concretes.ViceDean;
import com.schoolmanagement.payload.request.ViceDeanRequest;
import lombok.Data;

@Data
//component koymadim bean olsun diye CreateObjectBeande metoda Bean koyduk
public class ViceDeanDto {

    public ViceDean dtoViceDean(ViceDeanRequest viceDeanRequest){

        return ViceDean.builder()
                .birthDay(viceDeanRequest.getBirthDay())
                .username(viceDeanRequest.getUsername())
                .name(viceDeanRequest.getName())
                .surname(viceDeanRequest.getSurname())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .password(viceDeanRequest.getPassword())
                .ssn(viceDeanRequest.getSsn())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .gender(viceDeanRequest.getGender())
                .build();
    }
}
