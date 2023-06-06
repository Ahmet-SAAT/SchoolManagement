package com.schoolmanagement.service;

import com.schoolmanagement.entity.concretes.EducationTerm;
import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.request.EducationTermRequest;
import com.schoolmanagement.payload.response.EducationTermResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.EducationTermRepository;
import com.schoolmanagement.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducationTermService {

    private final EducationTermRepository educationTermRepository;


    //SAVE()********************************
    public ResponseMessage<EducationTermResponse> save(EducationTermRequest request) {

        //son kayit  tarihi baslangic  tarihinden sonra olmamali
        if (request.getLastRegistrationDate().isAfter(request.getStartDate())) {
            throw new ResourceNotFoundException(Messages.EDUCATION_START_DATE_IS_EARLIER_THAN_LAST_REGISTRATION_DATE);
        }

//Bitis tarihi baslangic tarihinden once olmamali

        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new ResourceNotFoundException(Messages.EDUCATION_END_DATE_IS_EARLIER_THAN_START_DATE);
        }

        //ayni term gun ve baslangic tarihine sahip birden fazla kayit  olmamali
        if (educationTermRepository.existsByTermAndYear(request.getTerm(), request.getStartDate().getYear())) {
            throw new ResourceNotFoundException(Messages.EDUCATION_TERM_IS_ALREADY_EXIST_BY_TERM_AND_YEAR_MESSAGE);
        }

        //dto-pojo donusumu
        EducationTerm savedEducationTerm = educationTermRepository.save(createEducationTerm(request));

        return ResponseMessage.<EducationTermResponse>builder()
                .message("Education Term Saved")
                .httpStatus(HttpStatus.CREATED)
                .object(createEducationTermResponse(savedEducationTerm))
                .build();
    }


    private EducationTerm createEducationTerm(EducationTermRequest request) {

        return EducationTerm.builder()
                .term(request.getTerm())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .lastRegistrationDate(request.getLastRegistrationDate())
                .build();
    }


    private EducationTermResponse createEducationTermResponse(EducationTerm term) {

        return EducationTermResponse.builder()
                .id(term.getId())
                .term(term.getTerm())
                .startDate(term.getStartDate())
                .endDate(term.getEndDate())
                .lastRegistrationDate(term.getLastRegistrationDate())
                .build();
    }


    //GET()**************************************************

    public EducationTermResponse get(Long id) {

        //ya yoksa
        if (!educationTermRepository.existsByIdEquals(id)) {//ExistsById de olurdu
            throw new ResourceNotFoundException(String.format(Messages.EDUCATION_TERM_NOT_FOUND_MESSAGE, id));
        }

        //pojoyu dtoya cevirelim
        return createEducationTermResponse(educationTermRepository.findByIdEquals(id));
        //findById de calisir uzatiyoruz


    }


    //GETALL()**************************************88
    public List<EducationTermResponse> getAll() {

        return educationTermRepository.findAll().
                stream().
                map(this::createEducationTermResponse).
                collect(Collectors.toList());


    }


    //GETALLWITHPAGE()***************************************************
    public Page<EducationTermResponse> getAllWithPage(int page, int size, String sort, String type) {

        Pageable pageable= PageRequest.of(page,size, Sort.by(sort).ascending());
        if (Objects.equals(type,"desc")){
             pageable=  PageRequest.of(page,size,Sort.by(sort).descending());
        }
        return educationTermRepository.findAll(pageable).map(this::createEducationTermResponse);

    }


    //DELETE()***************************88








    //UPDATE()*******************************************************





}
