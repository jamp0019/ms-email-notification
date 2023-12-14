package com.invexdijin.msemailnotification.adapters.in.controller;

import com.invexdijin.msemailnotification.application.core.domain.RequestContactEmail;
import com.invexdijin.msemailnotification.application.core.domain.RequestPdfEmail;
import com.invexdijin.msemailnotification.application.core.domain.Response;
import com.invexdijin.msemailnotification.application.ports.in.CreateEmailInputPort;
import com.itextpdf.text.DocumentException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/email")
public class EmailNotificationController {

    @Autowired
    private CreateEmailInputPort createEmailInputPort;

    @PostMapping("/contact")
    public ResponseEntity<?> sendContactEmail(@Valid @RequestBody RequestContactEmail requestContactEmail) {
        createEmailInputPort.createContactEmail(requestContactEmail);
        return ResponseEntity.ok().body(new Response("OK"));
    }

    @PostMapping("/pdf")
    public ResponseEntity<?> sendPdfByEmail(@Valid @RequestBody RequestPdfEmail requestPdfEmail) throws MessagingException, IOException {
        createEmailInputPort.sendMessageWithAttachment(requestPdfEmail);
        return ResponseEntity.ok().body(new Response("OK"));
    }

    @PostMapping("/pdf-style")
    public ResponseEntity<byte[]> exportPdf() throws IOException, DocumentException {
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("nombre","John");
        responseMap.put("message","Esta es un prueba");
        List<Map<String, Object>> queryResults = new ArrayList<>();
        queryResults.add(responseMap);
        ByteArrayOutputStream pdfStream = createEmailInputPort.generatePdfStream(queryResults);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=query_results.pdf");
        headers.setContentLength(pdfStream.size());
        return new ResponseEntity<>(pdfStream.toByteArray(), headers, HttpStatus.OK);
    }


}
