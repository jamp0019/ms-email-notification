package com.invexdijin.msemailnotification.application.core.usecase;

import com.invexdijin.msemailnotification.application.core.domain.RequestContactEmail;
import com.invexdijin.msemailnotification.application.core.domain.RequestPdfEmail;
import com.invexdijin.msemailnotification.application.core.exception.EmailInternalServerError;
import com.invexdijin.msemailnotification.application.ports.in.CreateEmailInputPort;
import com.invexdijin.msemailnotification.application.ports.out.MapperEmailPropertiesOutputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.util.Base64;

@Service
@Slf4j
public class CreateEmailUseCase implements CreateEmailInputPort {

    @Autowired
    private MapperEmailPropertiesOutputPort mapperEmailPropertiesOutputPort;

    @Autowired
    private JavaMailSender emailSender;

    private SimpleMailMessage message;
    @Override
    //@Async
    public void createContactEmail(RequestContactEmail requestContactEmail) {

        try {
            message = mapperEmailPropertiesOutputPort.mapping(requestContactEmail);
            emailSender.send(message);
            log.info("Email sent successfully");
        } catch (Exception ex){
            log.error("Email didn't send");
            log.error(ex.getMessage());
            throw new EmailInternalServerError(ex.getMessage());
        }

    }
    @Override
    @Async
    public void sendMessageWithAttachment(RequestPdfEmail requestPdfEmail) {

        try{
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("noreply@baeldung.com");
            helper.setTo(requestPdfEmail.getEmail());
            helper.setSubject("Reporte consolidado");
            helper.setText(requestPdfEmail.getMessage());
            byte[] pdf = Base64.getDecoder().decode(requestPdfEmail.getPdfBase64());
            helper.addAttachment("consolidated-report.pdf", new ByteArrayResource(pdf));
            emailSender.send(message);
            log.info("Email with attachment sent successfully");
        }catch (Exception ex){
            log.error("Email didn't send with attachment");
            log.error(ex.getMessage());
            throw new EmailInternalServerError(ex.getMessage());
        }

    }
}
