package com.schoolmanagement.service;

import com.schoolmanagement.entity.concretes.Dean;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.payload.dto.DeanDto;
import com.schoolmanagement.payload.request.AdminRequest;
import com.schoolmanagement.payload.request.DeanRequest;
import com.schoolmanagement.payload.response.DeanResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.DeanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service//bunu koymazsak bean olusmaz ve hata olur
@RequiredArgsConstructor
public class DeanService {

    private final DeanRepository deanRepository;
    private final AdminService adminService;
    private final DeanDto deanDto;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;

//save******************************
    public ResponseMessage<DeanResponse> save(DeanRequest deanRequest) {

        //dublicate kontrolu
        adminService.checkDuplicate(deanRequest.getUsername(),
                deanRequest.getSsn(),
                deanRequest.getPhoneNumber());
        //dto-pojo donusumu
       Dean dean=createDtoForDean(deanRequest);
       //role ve password bilgileri uygun sekilde setleniyor
       dean.setUserRole(userRoleService.getUserRole(RoleType.MANAGER));
       dean.setPassword(passwordEncoder.encode(dean.getPassword()));
       //DB ye kayit
       Dean savedDean=deanRepository.save(dean);


     return ResponseMessage.<DeanResponse>builder()
             .message("Dean Saved")
             .httpStatus(HttpStatus.CREATED)
             .object(createDeanResponse(savedDean)) //yardimci metod ile dto dondurecegiz.Sifre falan almasin
             .build();
    }

    private Dean createDtoForDean(DeanRequest deanRequest){

   return deanDto.dtoBean(deanRequest);

    }


    private DeanResponse createDeanResponse(Dean dean){
        return DeanResponse.
                builder()
                .userId(dean.getId())
                .username(dean.getUsername())
                .name(dean.getName())
                .surname(dean.getSurname())
                .birthDay(dean.getBirthDay())
                .birthPlace(dean.getBirthPlace())
                .phoneNumber(dean.getPhoneNumber())
                .ssn(dean.getSsn())
                .gender(dean.getGender()).
                build();
                //password ve role bilgisi dondurulmedi

    }

}
