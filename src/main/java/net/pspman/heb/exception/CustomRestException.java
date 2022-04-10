package net.pspman.heb.exception;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomRestException {

    private ZonedDateTime timestamp = ZonedDateTime.now();
    private int status;
    private String message;

    public CustomRestException(HttpStatus status) {
        this.status = status.value();
        this.message = status.getReasonPhrase();
    }
}
