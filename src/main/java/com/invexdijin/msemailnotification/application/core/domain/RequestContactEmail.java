package com.invexdijin.msemailnotification.application.core.domain;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestContactEmail {

    @NotNull(message = "contact-name-error-message")
    @NotEmpty(message = "contact-name-error-message")
    @NotBlank(message = "contact-name-error-message")
    private String contactName;

    @Email(message = "contact-mail-error-message-valid")
    @NotNull(message = "contact-mail-error-message")
    @NotEmpty(message = "contact-mail-error-message")
    @NotBlank(message = "contact-mail-error-message")
    @Size(min = 4, message = "contact-mail-error-message-characters")
    private String contactEmail;

    @NotNull(message = "contact-number-message")
    @NotEmpty(message = "contact-number-message")
    @NotBlank(message = "contact-number-message")
    @Pattern(regexp = "\\d+",message = "contact-number-message-number")
    private String contactPhone;

    @NotNull(message = "contact-message-error-message")
    @NotEmpty(message = "contact-message-error-message")
    @NotBlank(message = "contact-message-error-message")
    @Size(min = 5, message = "contact-message-error-message-characters")
    @Size(max = 400, message = "contact-message-error-message-characters-max")
    private String contactMessage;

}
