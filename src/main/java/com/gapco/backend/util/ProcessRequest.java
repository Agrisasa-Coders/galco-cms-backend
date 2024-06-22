package com.gapco.backend.util;

import com.gapco.backend.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProcessRequest {

    private final LogService logService;

    public JSONObject sendPostRequest(
            String body,
            Map<String,String> headers,
            String endpoint
    ){
        log.info("SendRequest::sendPostRequest Execution has started");
        log.info("SendRequest::sendPostRequest body is {}, url is {}",body,endpoint);

        logService.logToFile(AppConstants.LOGS_PATH,"SendPostRequest","Request: "+body);

        JSONObject response = new JSONObject();

        try{
            URL url = new URL(endpoint);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("POST");
            con.setDoOutput(true);

            //Setting headers
            if(headers != null){
                for(Map.Entry<String,String> entry: headers.entrySet()){
                    con.setRequestProperty(entry.getKey(),entry.getValue());
                }
            }

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = body.getBytes("utf-8");
                os.write(input, 0, input.length);
                os.flush();
            }

            int responseCode = con.getResponseCode();

            String result;
            BufferedReader in;
            String inputLine;
            StringBuffer responseBuffer;

            if(200 <= responseCode && responseCode <= 299)
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            else
                in = new BufferedReader(new InputStreamReader(con.getErrorStream()));


            responseBuffer = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                responseBuffer.append(inputLine);
            }
            result = responseBuffer.toString();
            in.close();

            log.info("SendRequest::sendPostRequest Response is {}",result );

            if(responseCode == HttpStatus.OK.value()){

                response.put("statusCode","200");
                response.put("result",result);

            } else {

                if(responseCode == 401)  //token expired
                {
                    response.put("statusCode","401");
                    response.put("result",result);

                } else {
                    response.put("statusCode","400");
                    response.put("result",result);
                }
            }

            log.info("SendRequest::sendPostRequest Response is {}",result );

            logService.logToFile(AppConstants.LOGS_PATH,"SendPostRequest","Response: "+result);

        }catch(Exception ex){
            response.put("statusCode","400");
            response.put("result",ex.getMessage());

            logService.logToFile(AppConstants.LOGS_PATH,"SendPostRequestException","Response: "+ex.getMessage());
        }

        return response;
    }


//    public void saveTransactionRequest(
//            PaymentRequest paymentRequest,
//            String transactionId,
//            String serviceType,
//            Long institutionId
//    ){
//
//        Institution institution = institutionRepository.findById(institutionId).get();
//
//        Transaction transaction = Transaction.builder()
//                .transactionId(transactionId)
//                .amount(paymentRequest.getAmount())
//                .transactionDescription(paymentRequest.getDescription())
//                .operator(paymentRequest.getOperator())
//                .serviceType(serviceType)
//                .recipient(paymentRequest.getRecipient())
//                .country(paymentRequest.getCountry())
//                .institution(institution)
//                .status(AppConstants.PENDING)
//                .requestTime(Helper.getCurrentTime())
//                .build();
//
//        transactionRepository.save(transaction);
//    }

}
