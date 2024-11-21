package com.github.jakubzmuda.centralControlStation.core.infrastructure;

import com.github.jakubzmuda.centralControlStation.core.application.BadRequestException;
import com.github.jakubzmuda.centralControlStation.core.application.NotFoundException;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.ForbiddenException;
import com.github.jakubzmuda.centralControlStation.usersAndAccess.domain.UnauthorizedException;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ApiExceptionResponse handle(BadRequestException exception) {
        return new ApiExceptionResponse(exception.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException.class)
    @ResponseBody
    public ApiExceptionResponse handle(ForbiddenException ignoredException) {
        return new ApiExceptionResponse("Forbidden");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    public ApiExceptionResponse handle(UnauthorizedException ignoredException) {
        return new ApiExceptionResponse("Unauthorized");
    }

}
