package com.tiki.profile.dto.response;

import com.tiki.profile.entity.Profile;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record ProfileResponse (
        String id,
        String firstName,
        String lastName,
        String username,
        String email,
        String city,
        String avatar,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dob
){

        public static ProfileResponse from(Profile profile) {
                return new ProfileResponse(profile.getId(),
                        profile.getFirstName(),
                        profile.getLastName(),
                        profile.getUsername(),
                        profile.getEmail(),
                        profile.getCity(),
                        profile.getAvatar(),
                        profile.getDob()
                        );
        }
}
