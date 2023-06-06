package com.schoolmanagement.controller;


import com.schoolmanagement.payload.request.EducationTermRequest;
import com.schoolmanagement.payload.response.EducationTermResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.service.EducationTermService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("educationTerms")
@RequiredArgsConstructor
public class EducationTermController {

    private final EducationTermService educationTermService;

    //save()***************************
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('ADMIN',MANAGER)")
    private ResponseMessage<EducationTermResponse> save(@Valid @RequestBody EducationTermRequest educationTermRequest){

    return educationTermService.save(educationTermRequest);

    }

//GETBYID()****************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER,'ASSISTANTMANAGER','TEACHER')")
    @GetMapping("/{id}")
   public EducationTermResponse get(@PathVariable Long id){
        return educationTermService.get(id);
    }


    //GETAll()****************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER,'ASSISTANTMANAGER','TEACHER')")
    @GetMapping("/getAll")
    public List<EducationTermResponse> getAll(){
        return educationTermService.getAll();
    }


    //GETAllWithPage()****************************************
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER,'ASSISTANTMANAGER','TEACHER')")
    @GetMapping("/search")//http://localhost:8080/educationTerms/search?page=0&size=10&sort=startDate&type=desc
    public Page<EducationTermResponse> getAllWithPage(
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size,
            @RequestParam(value = "sort",defaultValue = "startDate") String sort,
            @RequestParam(value = "type",defaultValue = "desc") String type
            ){

        return educationTermService.getAllWithPage(page,size,sort,type);

    }



    //DELETE()***************************88



    //UPDATE()*******************************************************






}
