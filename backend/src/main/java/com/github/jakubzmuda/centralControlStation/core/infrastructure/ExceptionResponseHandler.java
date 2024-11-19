package com.github.jakubzmuda.centralControlStation.core.infrastructure;

import com.github.jakubzmuda.centralControlStation.core.application.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Configuration
@ControllerAdvice
public class ExceptionResponseHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ApiExceptionResponse handle(NotFoundException ignoredException) {
        return new ApiExceptionResponse("Not found");
    }

}
