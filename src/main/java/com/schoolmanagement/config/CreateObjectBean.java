package com.schoolmanagement.config;

import com.schoolmanagement.payload.dto.DeanDto;
import com.schoolmanagement.payload.dto.ViceDeanDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CreateObjectBean {

    @Bean
    public DeanDto deanDto() {
        return new DeanDto();
    }
 //DeanDto clasina component yapmazsak bean bulunamaz ya component ekleriz ya da boyle yapariz.
 //Beanlari tek classa ekleyip beanlari goruruz.Kodu daha okunabilir yapar.

    @Bean
    public ViceDeanDto viceDeanDto() {
        return new ViceDeanDto();
    }
  //clasi enjecte ettik





}
