package com.tiki.profile.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record RegistrationRequest (
        @NotBlank
        String username,
        @Email
        String email,
        String password,
        String firstName,
        String lastName,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dob
) {
}
