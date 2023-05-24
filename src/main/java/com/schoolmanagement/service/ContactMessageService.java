package com.schoolmanagement.service;

import com.schoolmanagement.Utils.Messages;
import com.schoolmanagement.exception.ConflictException;
import com.schoolmanagement.payload.request.ContactMessageRequest;
import com.schoolmanagement.payload.response.ContactMessageResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static com.schoolmanagement.Utils.Messages.ALREADY_SEND_A_MESSAGE_TODAY;

@Service
@RequiredArgsConstructor//final olan fieldlaran cons olustu
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;//cons olustu

    //NOT---->save Methodu()************************
    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {
        //!!! ayni kisi ayni gun icinde sadece 1 defa mesaj gonderebilsin

       boolean isSameMessageWithSameEmailForToday=
               contactMessageRepository.existsByEmailEqualsAndDateEquals(contactMessageRequest.getEmail(), LocalDate.now());
    if (isSameMessageWithSameEmailForToday) throw new ConflictException(String.format(ALREADY_SEND_A_MESSAGE_TODAY));

        //DTO-POJO donusumu
    }
}
