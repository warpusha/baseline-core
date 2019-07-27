package com.baseline.sales.dto;

import com.baseline.sales.constant.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseDto {
    private Status status;
    private String message;
    private Object response;

    public static ResponseDto ok(Object response) {
        return new ResponseDto(Status.OK, null, response);
    }

    public static <T extends RuntimeException> ResponseDto error(T ex) {
        return new ResponseDto(Status.ERROR, ex.getMessage(), null);
    }

    public static ResponseDto error(String message) {
        return new ResponseDto(Status.ERROR, message, null);
    }
}
