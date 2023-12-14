package com.invexdijin.msemailnotification.application.ports.in;

import com.invexdijin.msemailnotification.application.core.domain.RequestContactEmail;
import com.invexdijin.msemailnotification.application.core.domain.RequestPdfEmail;
import com.itextpdf.text.DocumentException;

import javax.mail.MessagingException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CreateEmailInputPort {
    void createContactEmail(RequestContactEmail requestContactEmail);
    void sendMessageWithAttachment(RequestPdfEmail requestPdfEmail) throws MessagingException, IOException;

    ByteArrayOutputStream generatePdfStream(List<Map<String, Object>> queryResults) throws DocumentException;
}
