package com.schoolmanagement.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)//benzer objeler uretmek icin
@JsonInclude(JsonInclude.Include.NON_NULL)//json icerisinde null olanlarin gozukmememesini sagliyoruz
public class ResponseMessage<E> {

    private E object ;//dondurecegim data turu icin generic yaptik

    private String message;

    private HttpStatus httpStatus;

}
