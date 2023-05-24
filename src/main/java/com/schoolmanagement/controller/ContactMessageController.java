package com.schoolmanagement.controller;


import com.schoolmanagement.payload.request.ContactMessageRequest;
import com.schoolmanagement.payload.response.ContactMessageResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("contactMessages")
@RequiredArgsConstructor//final fieldalari ile cons olustu
public class ContactMessageController {

    private final ContactMessageService contactMessageService;//final fieldalari ile cons olustu

    //NOT-->save()***********************************
    @PostMapping("/save")
    public ResponseMessage<ContactMessageResponse> save(@Valid @RequestBody ContactMessageRequest contactMessageRequest) {
        return contactMessageService.save(contactMessageRequest);

    }


    //NOT-->getAll()***********************************

    //NOT-->searchByEmail()***********************************

    //NOT-->SearchBySubject()***********************************
}
