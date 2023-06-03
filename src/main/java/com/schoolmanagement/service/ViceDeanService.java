package com.schoolmanagement.service;


import com.schoolmanagement.entity.concretes.ViceDean;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.dto.ViceDeanDto;
import com.schoolmanagement.payload.request.ViceDeanRequest;
import com.schoolmanagement.payload.response.DeanResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.payload.response.ViceDeanResponse;
import com.schoolmanagement.repository.ViceDeanRepository;
import com.schoolmanagement.utils.CheckParameterUpdateMethod;
import com.schoolmanagement.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ViceDeanService {

    private AdminService adminService;
    private ViceDeanDto viceDeanDto;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;


    private final ViceDeanRepository viceDeanRepository;

    public ResponseMessage<ViceDeanResponse> save(ViceDeanRequest viceDeanRequest) {

        //duplicate controli
        adminService.checkDuplicate(viceDeanRequest.getUsername(),
                viceDeanRequest.getSsn(),
                viceDeanRequest.getPhoneNumber());//exceptoin verirse controllerde metoun return yerinde verir

        //dto-pojo cevirme
       ViceDean viceDean=createPojoFromDTO(viceDeanRequest);

        //role ve pasword bilgilerini setle
        viceDean.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANTMANAGER));
        viceDean.setPassword(passwordEncoder.encode(viceDeanRequest.getPassword()));
        //dbye kayit
        ViceDean savedViceDean=viceDeanRepository.save(viceDean);

        return ResponseMessage.<ViceDeanResponse>builder()
                .message("ViceDean Saved")
                .httpStatus(HttpStatus.CREATED)
                .object(createViceDeanResponse(savedViceDean))
                .build();
        //nicin dto olarak vicedeanrequest gondermiyoru.
        //validatatona gerek yok,bu clasda id yok,unique olan baska field yoksa objeye ulasamam.Idli dto koyalim.
        //mesela vicedeanrequestte ssn,username,phne uniqe olmazsa  sorun olurdu.
    }


    private ViceDean createPojoFromDTO(ViceDeanRequest viceDeanRequest){

       return viceDeanDto.dtoViceDean(viceDeanRequest);


    }


    private ViceDeanResponse createViceDeanResponse(ViceDean viceDean){
        return ViceDeanResponse.builder()
                .userId(viceDean.getId())
                .username(viceDean.getUsername())
                .ssn(viceDean.getSsn())
                .name(viceDean.getName())
                .gender(viceDean.getGender())
                .birthPlace(viceDean.getBirthPlace())
                .birthPlace(viceDean.getBirthPlace())
                .phoneNumber(viceDean.getPhoneNumber())
                .surname(viceDean.getSurname())
                .build();
    }

    //update()********************************
    public ResponseMessage<ViceDeanResponse> update(ViceDeanRequest newViceDean, Long managerId) {
    Optional<ViceDean> viceDean =viceDeanRepository.findById(managerId);

    if (!viceDean.isPresent()){
      throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER2_MESSAGE));
    } else if (!CheckParameterUpdateMethod.checkParameter(viceDean.get(),newViceDean)) {
        adminService.checkDuplicate(newViceDean.getUsername(),newViceDean.getSsn(),newViceDean.getPhoneNumber());
    }
       ViceDean updatedViceDean=createUpdatedViceDean(newViceDean,managerId);

       updatedViceDean.setUserRole(userRoleService.getUserRole(RoleType.ASSISTANTMANAGER));
       updatedViceDean.setPassword(passwordEncoder.encode(newViceDean.getPassword()));

       viceDeanRepository.save(updatedViceDean);

        return ResponseMessage.<ViceDeanResponse>builder()
                .message("ViceDean Updated")
                .httpStatus(HttpStatus.CREATED)
                .object(createViceDeanResponse(updatedViceDean))
                .build();
    }

    private ViceDean createUpdatedViceDean(ViceDeanRequest viceDeanRequest,Long Id){
        return ViceDean.builder()
                .id(Id)
                .ssn(viceDeanRequest.getSsn())
                .birthDay(viceDeanRequest.getBirthDay())
                .gender(viceDeanRequest.getGender())
                .name(viceDeanRequest.getName())
                .surname(viceDeanRequest.getSurname())
                .username(viceDeanRequest.getUsername())
                .birthPlace(viceDeanRequest.getBirthPlace())
                .phoneNumber(viceDeanRequest.getPhoneNumber())
                .build();


    }

    public ResponseMessage<?> deleteViceDean(Long managerId) {

        Optional<ViceDean> viceDean =viceDeanRepository.findById(managerId);

        if (!viceDean.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER2_MESSAGE));

        }
        viceDeanRepository.deleteById(managerId);

        return ResponseMessage.builder()
                .message("Vice Dean Deleted")
                .httpStatus(HttpStatus.OK)//deletede delete yok ok var update ve savede created
                .build();

    }

    public ResponseMessage<ViceDeanResponse> getViceDeanById(Long managerId) {

        Optional<ViceDean> viceDean =viceDeanRepository.findById(managerId);

        if (!viceDean.isPresent()) {
            throw new ResourceNotFoundException(String.format(Messages.NOT_FOUND_USER2_MESSAGE));
        }
        return ResponseMessage.<ViceDeanResponse>builder()
                .message("ViceDean Successfully found")
                .httpStatus(HttpStatus.OK)
                .object(createViceDeanResponse(viceDean.get()))
                .build();
    }

    public List<ViceDeanResponse> getAll() {

      return viceDeanRepository.findAll()
              .stream()
              .map(this::createViceDeanResponse)
              .collect(Collectors.toList());
    }




    public Page<ViceDeanResponse> getAllWithPage(int page, int size, String sort, String type) {

        Pageable pageable= PageRequest.of(page,size,Sort.by(sort).ascending());
        if (Objects.equals(type,"desc")){
            pageable=PageRequest.of(page,size,Sort.by(sort).descending());
        }

        return viceDeanRepository.findAll(pageable).map(this::createViceDeanResponse);//son metod ile dtoya cevirdik
    }
}
