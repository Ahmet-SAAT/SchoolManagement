package com.schoolmanagement.repository;

import com.schoolmanagement.entity.concretes.Lesson;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface LessonRepository extends JpaRepository<Lesson,Long> {

    boolean existsLessonByLessonNameEqualsIgnoreCase(String lessonname);

    Optional<Lesson> getLessonByLessonName(String lessonName);

    @Query("select l from Lesson l where l.lessonId IN : lessons")//id degerleri asagiaki degerlerle eslesenleri bul getir
    Set<Lesson> getLessonByLessonIdList(Set<Long> lessons);
}
