package com.gapco.backend.util;


import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateOperation {


    public  LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    public  Date convertFromLocalDateToDate(LocalDate localDateToConvert) {
        return Date.from(localDateToConvert.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
