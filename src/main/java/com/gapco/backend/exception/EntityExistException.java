package com.gapco.backend.exception;

public class EntityExistException extends RuntimeException{

    private static final long serialVersionUID=1L;

    public EntityExistException(String message){
        super(message);
    }
}
