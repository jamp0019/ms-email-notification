package com.invexdijin.msemailnotification.adapters.in.controller;

import com.invexdijin.msemailnotification.application.core.domain.RequestContactEmail;
import com.invexdijin.msemailnotification.application.core.domain.RequestPdfEmail;
import com.invexdijin.msemailnotification.application.core.domain.Response;
import com.invexdijin.msemailnotification.application.ports.in.CreateEmailInputPort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/invexdijin")
public class EmailNotificationController {

    @Autowired
    private CreateEmailInputPort createEmailInputPort;

    @PostMapping("/contact-form")
    public ResponseEntity<?> sendContactEmail(@Valid @RequestBody RequestContactEmail requestContactEmail) {
        createEmailInputPort.createContactEmail(requestContactEmail);
        return ResponseEntity.ok().body(new Response("OK"));
    }

    @PostMapping("/pdf")
    public ResponseEntity<?> sendPdfByEmail(@Valid @RequestBody RequestPdfEmail requestPdfEmail) throws MessagingException, IOException {
        createEmailInputPort.sendMessageWithAttachment(requestPdfEmail);
        return ResponseEntity.ok().body(new Response("OK"));
    }

}
