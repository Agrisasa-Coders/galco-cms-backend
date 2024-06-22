package com.gapco.backend.service;

import com.gapco.backend.model.Notification;
import com.gapco.backend.util.AppConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    private final LogService logService;

    @Async
    public void sendEmailNotification(Notification notification) {


        logService.logToFile(AppConstants.LOGS_PATH,"SendEmailNotification",notification.toString());

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(notification.getFrom());
        msg.setTo(notification.getTo());
        msg.setSubject(notification.getSubject());
        msg.setText(notification.getMessage());
        mailSender.send(msg);

//        MimeMessage message = mailSender.createMimeMessage();
//
//        try{
//            MimeMessageHelper helper = new MimeMessageHelper(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());
//
//            Context context = new Context();
//
//            Map<String,Object> props = new HashMap<>();
//            props.put("message",notification.getMessage());
//
//            context.setVariables(props);
//
//            String html = templateEngine.process("general-template",context);
//
//            helper.setTo(notification.getTo());
//            helper.setText(html, true);
//            helper.setSubject(notification.getSubject());
//            helper.setFrom(AppConstants.FROM_EMAIL);
//
//            mailSender.send(message);
//
//        }catch(Exception ex){
//            logService.logToFile(AppConstants.LOGS_PATH,"SendEmailNotification-Exception", ex.getMessage());
//        }
    }
}
