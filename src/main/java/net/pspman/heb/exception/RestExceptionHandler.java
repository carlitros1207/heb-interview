package net.pspman.heb.exception;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.BadRequestException;

import java.io.FileNotFoundException;
import java.net.MalformedURLException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadRequestException.class, MalformedURLException.class, FileNotFoundException.class})
    CustomRestException handleBadRequest(Exception e){
        log.error("Bad Request Error:",e);
        return new CustomRestException(HttpStatus.BAD_REQUEST);
    }
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class})
    CustomRestException handleNotFound(Exception e){
        log.error("Not Found Error:",e);
        return new CustomRestException(HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    CustomRestException handleAll(Exception e){
        log.error("Internal Server Error:",e);
        return new CustomRestException(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
