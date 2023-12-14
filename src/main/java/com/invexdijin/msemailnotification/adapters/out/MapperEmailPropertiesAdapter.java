package com.invexdijin.msemailnotification.adapters.out;

import com.invexdijin.msemailnotification.application.core.domain.RequestContactEmail;
import com.invexdijin.msemailnotification.application.ports.out.MapperEmailPropertiesOutputPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MapperEmailPropertiesAdapter implements MapperEmailPropertiesOutputPort {

    private SimpleMailMessage message;
    @Override
    public SimpleMailMessage mapping(RequestContactEmail requestContactEmail) {
        message = new SimpleMailMessage();
        message.setFrom(requestContactEmail.getEmail());
        message.setTo("john1992alex@gmail.com");
        message.setSubject("Contact form");
        message.setText(requestContactEmail.getName()+" "+
                requestContactEmail.getContactPhone()+" "+
                requestContactEmail.getMessage());
        return message;
    }
}
