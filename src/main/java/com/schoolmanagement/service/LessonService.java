package com.schoolmanagement.service;

import com.schoolmanagement.entity.concretes.Lesson;
import com.schoolmanagement.exception.ConflictException;
import com.schoolmanagement.exception.ResourceNotFoundException;
import com.schoolmanagement.payload.request.LessonRequest;
import com.schoolmanagement.payload.response.LessonResponse;
import com.schoolmanagement.payload.response.ResponseMessage;
import com.schoolmanagement.repository.LessonRepository;
import com.schoolmanagement.utils.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonService {

    private final LessonRepository lessonRepository;


    //SAVE()********************************************************
    public ResponseMessage<LessonResponse> save(LessonRequest lessonRequest) {
        if (existsLessonByLessonName(lessonRequest.getLessonName())) {
            throw new ConflictException(String.format(Messages.ALREADY_REGISTER_LESSON_MESSAGE, lessonRequest.getLessonName()));
        }
           Lesson lesson=createLessonObject(lessonRequest);
           lessonRepository.save(lesson);

           return ResponseMessage.<LessonResponse>builder()
                   .message("Lesson Created Successfully")
                   .httpStatus(HttpStatus.CREATED)
                   .object(createLessonResponse(lesson))
                   .build();


    }

    private boolean existsLessonByLessonName(String lessonname) {
        return lessonRepository.existsLessonByLessonNameEqualsIgnoreCase(lessonname);
    }


    private Lesson createLessonObject(LessonRequest request) {
        return Lesson.builder()
                .lessonName(request.getLessonName())
                .creditScore(request.getCreditScore())
                .isCompulsory(request.getIsCompulsory())
                .build();

    }

    private LessonResponse createLessonResponse(Lesson lesson){
        return LessonResponse.builder()
                .lessonId(lesson.getLessonId())
                .lessonName(lesson.getLessonName())
                .creditScore(lesson.getCreditScore())
                .isCompulsory(lesson.getIsCompulsory())
                .build();
    }




    //DELETE()*************************************************
    public ResponseMessage deleteLesson(Long id) {

        Lesson lesson=lessonRepository.findById(id).orElseThrow(
                //orElsethrow ile obje varsa ver yoksa exception firlat.optionale gerek kalmadi
                ()->{
                    return new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE,id));
                });
       lessonRepository.deleteById(id);

        return ResponseMessage.builder()
                .message("Lesson is deleted successfully")
                .httpStatus(HttpStatus.OK)
                .build();
    }



    //GETLESSONBYLESSONNAME
    public ResponseMessage<LessonResponse> getLessonByLessonName(String lessonName) {

        Lesson lesson=lessonRepository.getLessonByLessonName(lessonName).orElseThrow(()->{
           return new ResourceNotFoundException(String.format(Messages.NOT_FOUND_LESSON_MESSAGE,lessonName));
        });
        return ResponseMessage.<LessonResponse>builder()
                .message("Lesson Successfully found")
                .object(createLessonResponse(lesson))
                .build();
    }



    //GETALLLESSON()***********************************
    public List<LessonResponse> getAllLesson() {

        return lessonRepository.findAll().
                stream().map(this::createLessonResponse).
                collect(Collectors.toList());
    }



    // Not :  getAllWithPage() **********************************************************
    public Page<LessonResponse> search(int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return lessonRepository.findAll(pageable).map(this::createLessonResponse);
    }



    // Not :  getAllLessonByLessonIds() *****************************************************
    public Set<Lesson> getLessonByLessonIdList(Set<Long> lessons) {

        return lessonRepository.getLessonByLessonIdList(lessons);
    }

}
