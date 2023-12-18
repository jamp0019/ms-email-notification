package com.invexdijin.msemailnotification.adapters.out;

import com.invexdijin.msemailnotification.application.core.domain.RequestContactEmail;
import com.invexdijin.msemailnotification.application.ports.out.MapperEmailPropertiesOutputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MapperEmailPropertiesAdapter implements MapperEmailPropertiesOutputPort {

    private SimpleMailMessage message;

    @Value("${email.contact.set.to}")
    private String setTo;

    @Value("${email.contact.set.subject}")
    private String setSubject;
    @Override
    public SimpleMailMessage mapping(RequestContactEmail requestContactEmail) {
        message = new SimpleMailMessage();
        message.setFrom(requestContactEmail.getContactEmail());
        message.setTo(setTo);
        message.setSubject(setSubject);
        message.setText(requestContactEmail.getContactName()+" "+
                requestContactEmail.getContactPhone()+" "+
                requestContactEmail.getContactMessage());
        return message;
    }
}
