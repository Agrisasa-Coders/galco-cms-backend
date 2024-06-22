package com.gapco.backend.util;

import org.springframework.beans.factory.annotation.Value;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class EmailOperations {
    private static final Logger logger = LoggerFactory.getLogger(EmailOperations.class);

//    @Autowired
//    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    String senderName;
    @Value("${email.attachment.path}")
    private String emailAttachmentPath;
    String replyTo = "customersupport@accessmfb.co.tz";
    String customerSupportMail = "customersupport@accessmfb.co.tz";


//    private boolean sendEmailWithAttachment( String subject, String mail ,String ...sendTo) throws MessagingException {
//        try {
//            MimeMessage message = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setTo(sendTo);
//            helper.setSubject(subject);
//            helper.setText(mail);
//
////            Path path = Paths.get(emailAttachmentPath, attachment);
////            File file = path.toFile();
////            helper.addAttachment(file.getName(), file);
//
//            javaMailSender.send(message);
//            return true;
//        } catch (MessagingException e) {
//            logger.error("Failed to send email with attachment", e);
//            throw e;
//        }
//    }
//
//    private boolean sendSimpleEmail( String subject, String mail, String ...sendTo) {
//        try {
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setFrom(senderName);
//            message.setTo(sendTo);
//            message.setCc(customerSupportMail);
//            message.setReplyTo(replyTo);
//            message.setSubject(subject);
//            message.setText(mail);
//
//            javaMailSender.send(message);
//            return true;
//        } catch (Exception e) {
//            logger.error("Failed to send simple email", e);
//            return false;
//        }
//    }
//


}
