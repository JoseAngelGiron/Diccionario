package com.github.joseangelgiron.diccionario.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus (value = HttpStatus.NOT_FOUND)  //
public class PalabraNotFoundException extends RuntimeException {

    private String exceptionDetail;
    private Object fieldValue;

    public PalabraNotFoundException(String exceptionDetail, Object fieldValue) {
        super(exceptionDetail+" - "+fieldValue);
        this.exceptionDetail = exceptionDetail;
        this.fieldValue = fieldValue;
    }

    public String getExceptionDetail() {
        return exceptionDetail;
    }
    public Object getFieldValue() {
        return fieldValue;
    }
}