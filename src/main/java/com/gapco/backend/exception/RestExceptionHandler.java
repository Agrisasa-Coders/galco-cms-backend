package com.gapco.backend.exception;

import com.gapco.backend.response.CustomApiResponse;
import com.gapco.backend.util.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request){
        log.info("RestExceptionHandler::handleMethodArgumentNotValid Execution started");

        CustomApiResponse customApiResponse = new CustomApiResponse();

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage).collect(Collectors.toList());

        customApiResponse.setErrors(errors);
        customApiResponse.setErrorCount(ex.getErrorCount());
        customApiResponse.setStatusCode(AppConstants.FAILED_STATUS_CODE);
        customApiResponse.setMessage(AppConstants.MANDATORY_FIELD_MESSAGE);

        log.info("RestExceptionHandler::handleMethodArgumentNotValid Execution ended");
        return new ResponseEntity<>(customApiResponse,HttpStatus.OK);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.info("RestExceptionHandler::handleHttpMessageNotReadable Execution started");

        CustomApiResponse customApiResponse = new CustomApiResponse();

        List<String> errors = new ArrayList<>();
        errors.add("Bad request");

        customApiResponse.setErrors(errors);
        customApiResponse.setErrorCount(1);
        customApiResponse.setStatusCode(AppConstants.FAILED_STATUS_CODE);
        customApiResponse.setMessage("Failed to read request");

        log.info("RestExceptionHandler::handleHttpMessageNotReadable Execution ended");
        return new ResponseEntity<>(customApiResponse,HttpStatus.OK);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<CustomApiResponse> handleEntityNotFound(EntityNotFoundException ex){
        log.info("RestExceptionHandler::handleEntityNotFound Execution started");

        CustomApiResponse customApiResponse = new CustomApiResponse();

        List<String> errors = new ArrayList<>();
        errors.add("Entity not found");

        customApiResponse.setErrors(errors);
        customApiResponse.setErrorCount(1);
        customApiResponse.setStatusCode(AppConstants.FAILED_STATUS_CODE);
        customApiResponse.setMessage(ex.getMessage());

        log.info("RestExceptionHandler::handleEntityNotFound Execution started");
        return new ResponseEntity<>(customApiResponse,HttpStatus.OK);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<CustomApiResponse> globalExceptionHandler(InternalServerErrorException ex){

        CustomApiResponse customApiResponse = new CustomApiResponse();

        List<String> errors = new ArrayList<>();
        errors.add("There is an internal Error");

        customApiResponse.setErrors(errors);
        customApiResponse.setErrorCount(1);
        customApiResponse.setStatusCode(AppConstants.FAILED_STATUS_CODE);
        customApiResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(customApiResponse,HttpStatus.OK);
    }

    @ExceptionHandler(EntityExistException.class)
    public ResponseEntity<CustomApiResponse> handleEntityExistException(EntityExistException ex){


        CustomApiResponse customApiResponse = new CustomApiResponse();

        List<String> errors = new ArrayList<>();
        errors.add("Entity already exists");

        customApiResponse.setErrors(errors);
        customApiResponse.setErrorCount(1);

        customApiResponse.setStatusCode(AppConstants.FAILED_STATUS_CODE);
        customApiResponse.setMessage(ex.getMessage());

        return new ResponseEntity<>(customApiResponse,HttpStatus.OK);
    }


}
