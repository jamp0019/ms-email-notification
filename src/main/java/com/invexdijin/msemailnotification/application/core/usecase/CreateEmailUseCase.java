package com.invexdijin.msemailnotification.application.core.usecase;

import com.invexdijin.msemailnotification.application.core.domain.RequestContactEmail;
import com.invexdijin.msemailnotification.application.core.domain.RequestPdfEmail;
import com.invexdijin.msemailnotification.application.core.exception.EmailInternalServerError;
import com.invexdijin.msemailnotification.application.ports.in.CreateEmailInputPort;
import com.invexdijin.msemailnotification.application.ports.out.MapperEmailPropertiesOutputPort;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CreateEmailUseCase implements CreateEmailInputPort {

    @Autowired
    private MapperEmailPropertiesOutputPort mapperEmailPropertiesOutputPort;

    @Autowired
    private JavaMailSender emailSender;

    private SimpleMailMessage message;
    @Override
    @Async
    public void createContactEmail(RequestContactEmail requestContactEmail) {

        try {
            message = mapperEmailPropertiesOutputPort.mapping(requestContactEmail);
            emailSender.send(message);
            log.info("Email sent");
        } catch (Exception ex){
            log.error("Email didn't send");
            log.error(ex.getMessage());
            throw new EmailInternalServerError(ex.getMessage());
        }

    }

    @Override
    public void sendMessageWithAttachment(RequestPdfEmail requestPdfEmail) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("noreply@baeldung.com");
        helper.setTo(requestPdfEmail.getEmail());
        helper.setSubject("Test with file attached");
        helper.setText(requestPdfEmail.getMessage());

        //String base64Body = requestPdfEmail.getPdfBase64().split(",")[1];
        byte[] pdf = Base64.getDecoder().decode(requestPdfEmail.getPdfBase64());
        helper.addAttachment("consolidated-report.pdf", new ByteArrayResource(pdf));

        emailSender.send(message);
    }

    @Override
    public ByteArrayOutputStream generatePdfStream(List<Map<String, Object>> queryResults) throws DocumentException {

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();
        // Write column names
        Map<String, Object> firstRow = queryResults.get(0);
        for (String column : firstRow.keySet()) {
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Paragraph paragraph = new Paragraph(column, boldFont);
            document.add(paragraph);
        }
        document.add(new Paragraph("\n"));
        // Write data rows
        for (Map<String, Object> row : queryResults) {
            for (Object value : row.values()) {
                Paragraph paragraph = new Paragraph(value.toString());
                document.add(paragraph);
            }
            document.add(new Paragraph("\n"));
        }
        document.close();

        byte[] byteArrayImage = outputStream.toByteArray();
        String encodedImage = Base64.getEncoder().encodeToString(byteArrayImage);
        return outputStream;
    }
}
