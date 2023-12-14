package com.invexdijin.msemailnotification.application.core.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestContactEmail {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Email is mandatory")
    @Email
    private String email;
    @NotBlank(message = "Contact phone is mandatory")
    private String contactPhone;
    @NotBlank(message = "Message is mandatory")
    private String message;
}
