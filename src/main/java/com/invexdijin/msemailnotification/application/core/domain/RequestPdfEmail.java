package com.invexdijin.msemailnotification.application.core.domain;

import lombok.Data;

@Data
public class RequestPdfEmail {
    private String name;
    private String email;
    private String message;
    private String pdfBase64;

    RequestPdfEmail(){
        this.message="Sending the next information";
    }
}
