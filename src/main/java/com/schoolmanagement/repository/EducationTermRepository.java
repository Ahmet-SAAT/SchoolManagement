package com.schoolmanagement.repository;

import com.schoolmanagement.entity.concretes.EducationTerm;
import com.schoolmanagement.entity.enums.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EducationTermRepository extends JpaRepository<EducationTerm,Long> {


    @Query("SELECT (count(e) > 0 ) FROM EducationTerm e WHERE e.term=?1 AND EXTRACT(YEAR FROM e.startDate) = ?2")
    boolean existsByTermAndYear(Term term, int year);

    @Query("Select (count(e) > 0) FROM EducationTerm e where e.id=?1 ")
    boolean existsByIdEquals(Long id);


    @Query("Select e FROM EducationTerm e where e.id=?1 ")
    EducationTerm findByIdEquals(Long id);

}
