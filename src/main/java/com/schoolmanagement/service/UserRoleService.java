package com.schoolmanagement.service;

import com.schoolmanagement.entity.concretes.UserRole;
import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.repository.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRoleService {

private UserRoleRepository userRoleRepository;
    public UserRole getUserRole(RoleType roleType) {

        Optional<UserRole> userRole =userRoleRepository.findByERoleEquals(roleType);//turemesin diye araya E koyduk

        return userRole.orElse(null);//null degilse gonder

    }
}
