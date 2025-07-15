package com.tiki.profile.dto.request;


import java.time.LocalDate;

public record UpdateProfileRequest (
        String firstName,
        String lastName,
        LocalDate dob,
        String avatar,
        String city
){
}
