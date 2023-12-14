package com.invexdijin.msemailnotification.application.ports.out;

import com.invexdijin.msemailnotification.application.core.domain.RequestContactEmail;
import org.springframework.mail.SimpleMailMessage;

public interface MapperEmailPropertiesOutputPort {
    SimpleMailMessage mapping(RequestContactEmail requestContactEmail);
}
