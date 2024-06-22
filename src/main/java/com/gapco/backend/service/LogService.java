package com.gapco.backend.service;

import com.gapco.backend.util.FileLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LogService {

    @Async
    public void logToFile(String path, String key, String content){
        if(path!=null){
            try{
                FileLogger fileLogger = new FileLogger(path);
                fileLogger.createLog(key,content);
            }catch(Exception e){
                log.info("LogService::logToTheFile Exception occured with message {}",e.getMessage());
            }
        }
    }

}
