package com.loja.virtual.bitwise.controller;

import com.loja.virtual.bitwise.exception.ExceptionErro;
import com.loja.virtual.bitwise.model.dto.ObjetoErroDTO;
import com.loja.virtual.bitwise.service.ServiceSendEmail;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @Autowired
    private ServiceSendEmail serviceSendEmail;


    @ExceptionHandler(ExceptionErro.class)
    public ResponseEntity<Object> handleException(ExceptionErro ex) {
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        objetoErroDTO.setError(ex.getMessage());
        objetoErroDTO.setCode(HttpStatus.BAD_REQUEST.toString());

        return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.BAD_REQUEST);
    }

    @Override
    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class})
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        String message = "";

        if (ex instanceof MethodArgumentNotValidException) {
            List<ObjectError> errors = ((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors();

            for (ObjectError objectError : errors) {
                message += objectError.getDefaultMessage() + "\n";
            }

        } else if (ex instanceof HttpMessageNotReadableException) {
            message = "Não está sendo enviado dados no corpo da requisição";
        } else {
            message = ex.getMessage();
        }

        objetoErroDTO.setError(message);
        objetoErroDTO.setCode(status.value() + "[ERRO]: " + status.getReasonPhrase());

        try {
            serviceSendEmail.enviarEmailHtml("Erro na loja virtual",
                    ExceptionUtils.getStackTrace(ex), "jarmison6@gmail.com");

        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class, SQLException.class})
    protected ResponseEntity<Object> handleExceptionDataIntegry(Exception exception) {

        ObjetoErroDTO objetoErroDTO = new ObjetoErroDTO();

        String message = "";

        if (exception instanceof ConstraintViolationException) {
            message = "[ERRO INTERNO DE CHAVE ESTRANGEIRA]: " + ((ConstraintViolationException) exception).getCause().getCause().getMessage();
        } else {
            if (exception instanceof DataIntegrityViolationException) {
                message = "[ERRO INTERNO VIOLAÇÃO DE DADOS]: " + ((DataIntegrityViolationException) exception).getCause().getCause().getMessage();
            }
        }
        if (exception instanceof SQLException) {
            message = "[ERRO INTERNO VIOLAÇÃO DE DADOS]: " + ((SQLException) exception).getCause().getCause().getMessage();
        } else {
            message = exception.getMessage();
        }

        objetoErroDTO.setError(message);
        objetoErroDTO.setCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());

        return new ResponseEntity<Object>(objetoErroDTO, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

