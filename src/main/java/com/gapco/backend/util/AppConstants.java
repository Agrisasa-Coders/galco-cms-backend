package com.gapco.backend.util;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.RequestParam;

public class AppConstants {

    public static final String INSTITUTION_NAME = "ZFF";
    public static final String OTP_EMAIL_SUBJECT = INSTITUTION_NAME + " OTP";
    public static final String OTP_MESSAGE = "Your OTP code for ZFF is ";
    public static final String PASSWORD_EMAIL_SUBJECT = INSTITUTION_NAME + " PASSWORD";
    public static final String PASSWORD_MESSAGE = "Your Default Password for ZFF is ";
    public static final String BASE_URI = "/api/v1";
    public static final String LOGIN_PATH = "sign-on";

    public static final String BASE_URL = "https://smartstock-faas.bfast.fahamutech.com";

    public static final long ACCESS_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000*60*60*24*7L; //One week
    public static final long REFRESH_TOKEN_EXPIRE_TIME_IN_MILLISECONDS = 1000*60*60*24*2L; // Two days;

    public static final String FROM_EMAIL = "kekovasudi@gmail.com";
    public static final String FROM_INFOBIP = "Zaidi App";

    public static final String UN_AUTHENTICATED_USER = "default user";
    public static final String OTP_TEMPLATE = "otp";
    public static final String DEFAULT_PASSWORD_TEMPLATE = "password_default";

    //Sorting and Paging values
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";
    public static final String DEFAULT_SORT_BY = "id";
    public static final String DEFAULT_SORT_DIRECTION = "DESC";

    public static final String PAGE_NUMBER_DESCRIPTION = "Page number to display. Default is 0";
    public static final String PAGE_SIZE_DESCRIPTION = "Size of items for the page. Default is 10";
    public static final String SORT_BY_DESCRIPTION = "Column for sorting. Default is Id";
    public static final String SORT_DIRECTION_DESCRIPTION = "Sort direction. Default is DESC";


    //paths
    public static final String LOGS_PATH = "/var/log/zff/";  //logpath
    public static final String STORAGE_PATH = "/var/storage/zff/";

    //messages
    public static final String PASSWORD_EXPIRES_MESSAGE = "Password expires,needs to be reset";
    public static final String MAXIMUM_PASSWORD_ATTEMPT_MESSAGE = "No of password attempts exceeds the limit";
    public static final String EMAIL_DOES_NOT_EXIST_MESSAGE = "Email does not exists";
    public static final String INCORRECT_PASSWORD_MESSAGE = "Password is not correct";
    public static final String FAILING_TO_UPLOAD_PHOTO_MESSAGE = "There an error in updating profile photo";

    //Timezone
    public static final String TIMEZONE = "Africa/Dar_es_Salaam";

    //TODO finding the best way to secure credentials
    public static final String PAYMENT_GATEWAY_USERNAME = "";
    public static final String PAYMENT_GATEWAY_PASSWORD = "";

    //TRANSACTION STATUSES
    public static final int SUCCESS = 200;
    public static final int FAILED = 400;
    public static final int PENDING = 100;

    //AUDIT EVENTS
    public static String CREATE = "0";
    public static String UPDATE = "1";
    public static String DELETE = "2";

    //SERVICES IDENTIFICATION
    public static final String NAME_RESOLVING = "001";
    public static final String DISBURSE = "101";


    /**response status code**/
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int FAILED_STATUS_CODE = 400; // GENERAL ERROR
    public static final int DEVICE_NOT_REGISTERED_CODE = 1000;   //DEVICE NOT REGISTERED

    /**common messages**/
    public static final String MANDATORY_FIELD_MESSAGE = "Mandatory field(s) are required";
    public static final String OPERATION_SUCCESSFULLY_MESSAGE = "Operation Successfully";


}
