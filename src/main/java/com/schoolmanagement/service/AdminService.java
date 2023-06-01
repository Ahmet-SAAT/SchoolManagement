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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;


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
    private final PasswordEncoder passwordEncoder;


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
     //password encode ediliyor
     admin.setPassword(passwordEncoder.encode(admin.getPassword()));


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

        /*// burada amac BaseReposiyory sinifini JpaRepositoryden extend edip, ana repository lerime JPA ozelligini kazandirmak

        public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
            boolean existsByUsername(String username);
            boolean existsBySsn(String ssn);
            boolean existsByPhoneNumber(String phoneNumber);
        }
        @Repository
        public interface AdminRepository extends BaseRepository<Admin, Long> {
            // Diğer metotlar...
        }

        @Repository
        public interface DeanRepository extends BaseRepository<Dean, Long> {
            // Diğer metotlar...
        }*/






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

    public Page<Admin> getAllAdmin(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    // Not: delete() *******************************************************
    public String deleteAdmin(Long id) {

        Optional<Admin> admin = adminRepository.findById(id);

        if(admin.isPresent() && admin.get().isBuilt_in()) {
            throw new ConflictException(Messages.NOT_PERMITTED_METHOD_MESSAGE);
        }

        if(admin.isPresent()) {
            adminRepository.deleteById(id);

            return "Admin is deleted Successfully";
        }

        return Messages.NOT_FOUND_USER_MESSAGE;
    }

    // !!! Runner tarafi icin yazildi
    public long countAllAdmin() {

        return adminRepository.count();
    }




    /*@SuperBuilder --> ilgili sinifin field'larini bu classdan türetilen siniflara aktarirken (sadece java tarafinda)
    @MappedSuperclass --> annotation'u da db de table olusturmamasina ragmen türettigi entitylere field'larini aktariyor
    ve türetilen entity siniflarinin db de kolonlarinin olusmasini sagliyor*/


}
