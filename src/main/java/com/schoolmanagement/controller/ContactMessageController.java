package com.schoolmanagement.controller;


import com.schoolmanagement.payload.request.ContactMessageRequest;
import com.schoolmanagement.payload.response.ContactMessageResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.service.ContactMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
    @GetMapping("/getAll")
    public Page<ContactMessageResponse> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size,
                                               @RequestParam(value = "sort", defaultValue = "date") String sort,
                                               @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return contactMessageService.getAll(page, size, sort, type);

    }


    //NOT-->searchByEmail()***********************************
    @GetMapping("/searchByEmail")
    public Page<ContactMessageResponse> searchByEmail(@RequestParam(value = "email") String email,
                                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                                      @RequestParam(value = "sort", defaultValue = "date") String sort,
                                                      @RequestParam(value = "type", defaultValue = "desc") String type) {

        return contactMessageService.searchByEmail(email, page, size, sort, type);
    }


    //NOT-->SearchBySubject()***********************************
    @GetMapping("/searchByEmail")
    public Page<ContactMessageResponse> searchBySubject(@RequestParam(value = "subject") String subject,
                                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                                        @RequestParam(value = "sort", defaultValue = "date") String sort,
                                                        @RequestParam(value = "type", defaultValue = "desc") String type) {

        return contactMessageService.searchBySubject(subject, page, size, sort, type);
    }


}
//postmande bunlarin testini yapalim