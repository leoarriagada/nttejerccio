package cl.ntt.postulacion.handler;

import cl.ntt.postulacion.response.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

@RestControllerAdvice
public class GlobalExceptionsHandler {

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @ExceptionHandler({
        ConstraintViolationException.class,
        HttpClientErrorException.BadRequest.class,
        MethodArgumentNotValidException.class,
        MissingServletRequestParameterException.class,
        IllegalArgumentException.class
    })
    public ResponseEntity<ErrorResponse> constraintViolationException(Exception e) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "BAD REQUEST", e);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> forbiddenException(Exception e) {
        return generateErrorResponse(HttpStatus.UNAUTHORIZED, "NOT ALLOWED", e);
    }

    @ExceptionHandler({
        EntityNotFoundException.class,
        NoSuchElementException.class,
        NoResultException.class,
        EmptyResultDataAccessException.class,
        IndexOutOfBoundsException.class,
        NullPointerException.class
    })
    public ResponseEntity<ErrorResponse> notFoundException(Exception e) {
        return generateErrorResponse(HttpStatus.NOT_FOUND, "NOT FOUND", e);
    }

    @ExceptionHandler({
        Exception.class,
        SQLException.class,
        SQLClientInfoException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponse> internalServerErrorException(Exception e) {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL SERVER ERROR", e);
    }

    private ResponseEntity<ErrorResponse> generateErrorResponse(
        HttpStatus status,
        String message,
        Exception e
    ) {
        String stackTraceMessage = (activeProfile.equals("dev")) ? e.getMessage() : e.getMessage();
        return ResponseEntity.status(status).body(new ErrorResponse(status, message, stackTraceMessage));
    }
}