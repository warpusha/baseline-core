package com.baseline.salescore.dto;

import com.baseline.salescore.constants.Status;
import lombok.Data;

@Data
public class ResponseDto {
    private Status status;
    private String statusMessage;
    private Object response;

}
