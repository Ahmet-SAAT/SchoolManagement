package com.schoolmanagement.service;

import com.schoolmanagement.Utils.Messages;
import com.schoolmanagement.controller.ContactMessageController;
import com.schoolmanagement.entity.concretes.ContactMessage;
import com.schoolmanagement.exception.ConflictException;
import com.schoolmanagement.payload.request.ContactMessageRequest;
import com.schoolmanagement.payload.response.ContactMessageResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;

import static com.schoolmanagement.Utils.Messages.ALREADY_SEND_A_MESSAGE_TODAY;

@Service
@RequiredArgsConstructor//final olan fieldlaran cons olustu
public class ContactMessageService {



    private final ContactMessageRepository contactMessageRepository;//cons olustu

    //**********************getAll()methodu**********************
    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return contactMessageRepository.findAll(pageable).map(this::createdResponse);//createdResponse metodunu cagirdim
    }

    //****************************SearchByEmail()methodu*********************************
    public Page<ContactMessageResponse> searchByEmail(String email, int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return contactMessageRepository.findByEmailEquals(email, pageable).map(this::createdResponse);
    }

    //****************************SearchBySubject()methodu*********************************
    public Page<ContactMessageResponse> searchBySubject(String subject, int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }

        return contactMessageRepository.findBySubjectEquals(subject,pageable).map(this::createdResponse);

    }


    //NOT---->save Methodu()************************
    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {
        //!!! ayni kisi ayni gun icinde sadece 1 defa mesaj gonderebilsin

        boolean isSameMessageWithSameEmailForToday =
                contactMessageRepository.existsByEmailEqualsAndDateEquals(contactMessageRequest.getEmail(), LocalDate.now());
        if (isSameMessageWithSameEmailForToday)
            throw new ConflictException(String.format(ALREADY_SEND_A_MESSAGE_TODAY));
//nicin string.format kullandm.Cunku string ifade yerine id gibi numeric degerlerde girilebilirdi bunu stringe cevir dedik.


        //DTO-POJO donusumu
        ContactMessage contactMessage = createObject(contactMessageRequest);//create objecti cagirdim.
        ContactMessage savedData = contactMessageRepository.save(contactMessage);
        return ResponseMessage.<ContactMessageResponse>builder().
                message("Contact Message Created Succesfully")
                .httpStatus(HttpStatus.CREATED)
                .object(createdResponse(savedData)).//createdResponse metodunu cagirdim
                        build();
        //obje icine contactMessageRequest yazabilirim ama cliente gidecek data icin gereksiz validation yapacak.
    }

    //DTO Pojo donusumu icin yardimci method

    private ContactMessage createObject(ContactMessageRequest contactMessageRequest) {
     /*   contactMessage.setMessage(contactMessageRequest.getMessage());
        contactMessage.setEmail(contactMessageRequest.getEmail());
        contactMessage.setName(contactMessageRequest.getName());
        contactMessage.setSubject(contactMessageRequest.getSubject());*///boyle yapmistim ama builder ile daha clean

        return ContactMessage.builder().
                name(contactMessageRequest.getName())
                .subject(contactMessageRequest.getSubject())
                .email(contactMessageRequest.getEmail())
                .message(contactMessageRequest.getMessage())
                .date(LocalDate.now()).build();//yeni bir contactmessage objesi olusturduk
        //id kendisi olusacak.
    }

    //POJO DTO donusumu icin yardimci metod
    private ContactMessageResponse createdResponse(ContactMessage contactMessage) {
        return ContactMessageResponse.builder()
                .name(contactMessage.getName())
                .message(contactMessage.getMessage())
                .email(contactMessage.getEmail())
                .subject(contactMessage.getSubject())
                .date(LocalDate.now()).build();
    }


}
