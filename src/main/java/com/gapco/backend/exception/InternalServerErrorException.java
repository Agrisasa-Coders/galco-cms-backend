package com.gapco.backend.exception;

import com.gapco.backend.service.LogService;

public class InternalServerErrorException extends RuntimeException{

    private static final long serialVersionUID=1L;

    private LogService logService;


    public InternalServerErrorException(String message) {
        super(message);
    }

    public InternalServerErrorException(String message,String logKey, String logPath,LogService logService) {
        super(message);
        this.logService = logService;
        this.logService.logToFile(logKey,logKey,message);
    }
}
