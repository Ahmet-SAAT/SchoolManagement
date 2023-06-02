package com.schoolmanagement.repository;

import com.schoolmanagement.entity.concretes.UserRole;
import com.schoolmanagement.entity.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {


    @Query("select r from UserRole r where r.roleType = ?1")//1 1.parametreyi ver demek.2 desem 2 parametre olurdu.
    //Ama burada tek parametre var.
    Optional<UserRole> findByERoleEquals(RoleType roleType);



    @Query("select (count(r)>0) from UserRole r where r.roleType = ?1")
    boolean existsByERoleEquals(RoleType roleType);

}
