package com.schoolmanagement.service;

import com.schoolmanagement.entity.concretes.Admin;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.exception.ConflictException;
import com.schoolmanagement.payload.request.concretes.AdminRequest;
import com.schoolmanagement.payload.response.AdminResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.*;
import com.schoolmanagement.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final StudentRepository studentRepository;
    private final ViceDeanRepository viceDeanRepository;
    private final DeanRepository deanRepository;
    private final TeacherRepository teacherRepository;
    private final GuestUserRepository guestUserRepository;
    private final UserRoleService userRoleService;


    //save()**********************************
    public ResponseMessage save(AdminRequest request) {
        //girilen username- ssn-phonenumber unique mi kontroluu
     checkDuplicate(request.getUsername(),request.getSsn(),request.getPhoneNumber());
     //admin nesnesi builder ile olusturalim
        Admin admin= createAdminForSave(request);
        admin.setBuilt_in(false);
        if (Objects.equals(request.getUsername(),"Admin")) admin.setBuilt_in(true);

     //admin rolu veriliyor
     admin.setUserRole(userRoleService.getUserRole(RoleType.ADMIN));
// TODO: 5/27/2023  //NOT:password plain text encode edilecek.Security yapilirken


        Admin savedData=adminRepository.save(admin);
        return ResponseMessage.< AdminResponse >builder().
                message("Admin saved")
                .httpStatus(HttpStatus.CREATED)
                .object(createdResponse(savedData))//elimde pojo var pojoyu dtoya cevirip vermeliyim
                .build();
    }

    public void checkDuplicate(String username,String ssn,String phone){
      if (adminRepository.existsByUsername(username)||
        deanRepository.existsByUsername(username)||
        studentRepository.existsByUsername(username)||
        teacherRepository.existsByUsername(username)||
        viceDeanRepository.existsByUsername(username)||
        guestUserRepository.existsByUsername(username))
      {
          throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_USERNAME,username));
          //username %s ifadesinin yeerine gelecek
        }else if(adminRepository.existsBySsn(ssn)||
              deanRepository.existsBySsn(ssn)||
              studentRepository.existsBySsn(ssn)||
              teacherRepository.existsBySsn(ssn)||
              viceDeanRepository.existsBySsn(ssn)||
              guestUserRepository.existsBySsn(ssn))
        {
          throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_SSN,ssn));
      } else if (adminRepository.existsByPhoneNumber(phone)||
              deanRepository.existsByPhoneNumber(phone)||
              studentRepository.existsByPhoneNumber(phone)||
              teacherRepository.existsByPhoneNumber(phone)||
              viceDeanRepository.existsByPhoneNumber(phone)||
              guestUserRepository.existsByPhoneNumber(phone)) {
          throw new ConflictException(String.format(Messages.ALREADY_REGISTER_MESSAGE_PHONE_NUMBER,phone));

      }


    }



    protected Admin createAdminForSave(AdminRequest request){
        return Admin.builder().
                username(request.getUsername())
                .name(request.getName())
                .surname(request.getSurname())
                .ssn(request.getSsn())
                .birthDay(request.getBirthDay())
                .phoneNumber(request.getPhoneNumber())
                .gender(request.getGender())
                .build();
    }


    private AdminResponse createdResponse(Admin admin){
        return AdminResponse.builder()
                .userId(admin.getId())
                .username(admin.getUsername())
                .name(admin.getName())
                .surname(admin.getSurname())
                .phoneNumber(admin.getPhoneNumber())
                .gender(admin.getGender())
                .ssn(admin.getSsn()).build();
    }



}
