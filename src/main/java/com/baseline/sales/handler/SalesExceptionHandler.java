package com.baseline.sales.handler;

import com.baseline.sales.dto.ResponseDto;
import com.baseline.sales.exception.ItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class SalesExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ItemNotFoundException.class)
    public HttpEntity<ResponseDto> handleItemNotFoundException(HttpServletRequest req, ItemNotFoundException ex) {
        return handle(req, ex);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public HttpEntity<ResponseDto> handleException(HttpServletRequest req, Exception ex) {
        return handle(req, ex);
    }

    private HttpEntity<ResponseDto> handle(HttpServletRequest req, Exception ex) {
        log.error(String.format("Error on %s request to %s", req.getMethod(), req.getRequestURL()), ex);
        return new HttpEntity<>(ResponseDto.error(ex.getMessage()));
    }

}
