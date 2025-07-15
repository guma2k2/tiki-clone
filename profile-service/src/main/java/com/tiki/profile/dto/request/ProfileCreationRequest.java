package com.tiki.profile.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ProfileCreationRequest (
      @NotBlank
      String userId,
      @NotBlank
      String username,
      @Email
      String email,
      String firstName,
      String lastName,
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
      LocalDate dob,
      String avatar,
      String city
) {
}
