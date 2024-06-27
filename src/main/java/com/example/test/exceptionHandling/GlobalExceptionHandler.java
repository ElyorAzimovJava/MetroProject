package com.example.test.exceptionHandling;

import com.example.test.dto.exception.ExceptionResponse;
import com.example.test.exceptions.DataAlreadyExists;
import com.example.test.exceptions.DataNotFoundException;
import com.example.test.exceptions.InputException;
import com.example.test.exceptions.LoginException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.xml.stream.util.EventReaderDelegate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {


    /*@ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        ex.printStackTrace();
        List<ExceptionResponse> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String[] codes = error.getCodes();
            assert codes != null;
            String code = codes[codes.length - 1];
            String errorMessage = error.getDefaultMessage() + "_" + code;
            FieldError fieldError = (FieldError) error;
            errors.add(new ExceptionResponse(errorMessage, HttpStatus.BAD_REQUEST.value(),request.getRequestURI(), LocalDateTime.now()));
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }*/

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ExceptionResponse> validatorException(ConstraintViolationException exception,HttpServletRequest request){
        ExceptionResponse exceptionDto = new ExceptionResponse(exception.getMessage(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI(),LocalDateTime.now());
        return new ResponseEntity<>(exceptionDto,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(BindException.class)
    public ResponseEntity<List<ExceptionResponse>> handleBindException(BindException ex,HttpServletRequest request) {
        ArrayList<ExceptionResponse> exceptionDtos = new ArrayList<>();
        StringBuilder errorMessage = new     StringBuilder();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessage.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());
           exceptionDtos.add( new ExceptionResponse(errorMessage.toString(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI(),LocalDateTime.now()));
            errorMessage = new StringBuilder();
        }
        return new ResponseEntity<>(exceptionDtos,HttpStatus.BAD_REQUEST);}

    @ExceptionHandler(value = DataNotFoundException.class)
    public ResponseEntity<ExceptionResponse> dataNotFoundExceptionHandler(
            DataNotFoundException notFoundException,HttpServletRequest request
    ){
        String requestURI = request.getRequestURI();
        String message = notFoundException.getMessage();
        Integer statusCode = HttpStatus.NOT_FOUND.value();
        ExceptionResponse error = new ExceptionResponse(message, statusCode, requestURI, LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = LoginException.class)
    public ResponseEntity<ExceptionResponse> loginExceptionHandler(
            LoginException notFoundException,HttpServletRequest request
    ){
        String requestURI = request.getRequestURI();
        String message = notFoundException.getMessage();
        Integer statusCode = HttpStatus.UNAUTHORIZED.value();
        ExceptionResponse error = new ExceptionResponse(message, statusCode, requestURI, LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<ExceptionResponse> handlingRuntime(
            RuntimeException runtimeException,HttpServletRequest request
    ){
        String requestURI = request.getRequestURI();
        String message = runtimeException.getMessage();
        Integer statusCode = HttpStatus.INTERNAL_SERVER_ERROR.value();
        ExceptionResponse error = new ExceptionResponse(message, statusCode, requestURI, LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = DataAlreadyExists.class)
    public ResponseEntity<ExceptionResponse> alreadyExistsExceptionHandler(
            DataAlreadyExists exception,HttpServletRequest request
    ){
        String requestURI = request.getRequestURI();
        String message = exception.getMessage();
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        ExceptionResponse error = new ExceptionResponse(message, statusCode, requestURI, LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = InputException.class)
    public ResponseEntity<ExceptionResponse> inputExceptionHandler(
            InputException exception,HttpServletRequest request
    ){
        String requestURI = request.getRequestURI();
        String message = exception.getMessage();
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        ExceptionResponse error = new ExceptionResponse(message, statusCode, requestURI, LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ExceptionResponse> handleException(HttpRequestMethodNotSupportedException exception,HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String message = exception.getMessage();
        Integer statusCode = HttpStatus.METHOD_NOT_ALLOWED.value();
        ExceptionResponse error = new ExceptionResponse(message, statusCode, requestURI, LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<ExceptionResponse> handleException(AccessDeniedException exception,HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String message = exception.getMessage();
        Integer statusCode = HttpStatus.FORBIDDEN.value();
        ExceptionResponse error = new ExceptionResponse(message, statusCode, requestURI, LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(value = {HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<?> handleException(HttpMediaTypeNotAcceptableException ex, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String message = ex.getMessage();
        Integer statusCode = HttpStatus.NOT_ACCEPTABLE.value();
        ExceptionResponse error = new ExceptionResponse(message, statusCode, requestURI, LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.NOT_ACCEPTABLE);
    }
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwtException(ExpiredJwtException e) {
        return new ResponseEntity<>("JWT expired: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
 /*   @ExceptionHandler(value = {InputException.class})
    public ResponseEntity<ExceptionResponse> handleInputException(InputException e, HttpServletRequest request){
        String requestURI = request.getRequestURI();
        String message = e.getMessage();
        Integer statusCode = HttpStatus.BAD_REQUEST.value();
        ExceptionResponse error = new ExceptionResponse(message, statusCode, requestURI, LocalDateTime.now());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }*/


   /* @ExceptionHandler(value = {TypeMismatchException.class})
    public ResponseEntity<?> handleException(TypeMismatchException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResponseEntity<?> handleException(MissingServletRequestParameterException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ServletRequestBindingException.class})
    public ResponseEntity<?> handleException(ServletRequestBindingException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MissingServletRequestPartException.class})
    public ResponseEntity<?> handleException(MissingServletRequestPartException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {BindException.class})
    public ResponseEntity<?> handleException(BindException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 400),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<?> handleException(AccessDeniedException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(MessageConstants.DO_NOT_HAVE_PERMISSION_TO_USE_THIS_WAY, 403), //"Bu yo'lga kirishga huquq yo'q"
                HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler(value = {MissingPathVariableException.class})
    public ResponseEntity<?> handleException(MissingPathVariableException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(MessageConstants.PAGE_NOT_FOUND, 404), //ex.me
                HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = {NoHandlerFoundException.class})
    public ResponseEntity<?> handleException(NoHandlerFoundException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 404),
                HttpStatus.NOT_FOUND);
    }
    //METHOD XATO BO'LSA
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<?> handleException(HttpRequestMethodNotSupportedException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(MessageConstants.METHOD_ERROR, 405),
                HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = {HttpMediaTypeNotAcceptableException.class})
    public ResponseEntity<?> handleException(HttpMediaTypeNotAcceptableException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(MessageConstants.UNACCEPTABLE, 406),
                HttpStatus.NOT_ACCEPTABLE);
    }
    //METHOD XATO BO'LSA
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<?> handleException(HttpMediaTypeNotSupportedException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(MessageConstants.METHOD_ERROR, 415),
                HttpStatus.METHOD_NOT_ALLOWED);
    }
    @ExceptionHandler(value = {ConversionNotSupportedException.class})
    public ResponseEntity<?> handleException(ConversionNotSupportedException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = {HttpMessageNotWritableException.class})
    public ResponseEntity<?> handleException(HttpMessageNotWritableException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<?> handleException(Exception ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(MessageConstants.SOMETHING_WENT_WRONG, 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {AsyncRequestTimeoutException.class})
    public ResponseEntity<?> handleException(AsyncRequestTimeoutException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 503),
                HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(value = {StringIndexOutOfBoundsException.class})
    public ResponseEntity<?> handleException(StringIndexOutOfBoundsException ex) {
        ex.printStackTrace();
        return new ResponseEntity<>(
                Response.errorResponse(ex.getMessage(), 500),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UsernameNotFoundException e){
        return new ResponseEntity<>(Response.errorResponse(e.getMessage(),401),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<?> handleDataNotFound(DataNotFoundException e){
        return new ResponseEntity<>(Response.errorResponse(e.getMessage(),401),HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handlerRuntimeException(RuntimeException e){
        return new ResponseEntity<>(Response.errorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<?> handleTokenExpired(TokenExpiredException e){
        return new ResponseEntity<>(Response.errorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<?> handleClientError(HttpClientErrorException e){
        return new ResponseEntity<>(Response.errorResponse(e.getMessage(),HttpStatus.BAD_REQUEST.value()),HttpStatus.BAD_REQUEST);
    }
*/
}
