package com.invexdijin.msemailnotification.application.ports.in;

import com.invexdijin.msemailnotification.application.core.domain.RequestContactEmail;
import com.invexdijin.msemailnotification.application.core.domain.RequestPdfEmail;

public interface CreateEmailInputPort {
    void createContactEmail(RequestContactEmail requestContactEmail);
    void sendMessageWithAttachment(RequestPdfEmail requestPdfEmail);

}
