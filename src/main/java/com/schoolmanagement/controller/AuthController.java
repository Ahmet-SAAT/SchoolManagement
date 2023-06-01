package com.schoolmanagement.controller;

import com.schoolmanagement.entity.enums.RoleType;
import com.schoolmanagement.payload.request.LoginRequest;
import com.schoolmanagement.payload.response.AuthResponse;
import com.schoolmanagement.security.jwt.JwtUtils;
import com.schoolmanagement.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    public final JwtUtils jwtUtils;
    public final AuthenticationManager authenticationManager;

    @PostMapping("/login")//http://localhost:8080/auth/login
    public ResponseEntity<AuthResponse> authenticateUser( @RequestBody @Valid LoginRequest loginRequest){

        //!!! Gelen requestin icinden username ve password bilgisi aliniyor.
        String username=loginRequest.getUsername();
        String password=loginRequest.getPassword();

        //authenticationManager uzerinden kullaniciyi valide ediyoruz
        Authentication authentication =authenticationManager.
                authenticate(new UsernamePasswordAuthenticationToken(username,password));

        //kullaniciyi generate oncesi contexte attik
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //JWT token olusturuluyor
        String token="Bearer "+jwtUtils.generateJwtToken(authentication);

        //GrantedAutherity tipindeki role yapini string tipine cevirme
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();//anlik olarak login islemini gerceklerstiren userdetails gonderir
        //userdetailsde role yok greantedautority var ama benim role setlemem lazim

        Set<String> roles=userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)//grantedAuthority stringe cevrilerek roller anlayacagimiz sekilde
                .collect(Collectors.toSet());

        Optional<String> role =roles.stream().findFirst();

        //AutResponse
        AuthResponse.AuthResponseBuilder authResponse=AuthResponse.builder();
        authResponse.username(userDetails.getUsername());
        authResponse.token(token);
        authResponse.name(userDetails.getName());

        //!!!Role mevcutsa ve TEACHER ise advisor durumu setleniyor
        if (role.isPresent()){
            authResponse.role(role.get());
            if (role.get().equalsIgnoreCase(RoleType.TEACHER.name())){
                authResponse.iAdvisor(userDetails.getIsAdvisor().toString());
            }
        }

        //AuthResponse nesnesi ResponseEntity ile donduruyoruz
        return ResponseEntity.ok(authResponse.build());
    }


}
