package com.tiki.profile.controller;

import com.tiki.profile.dto.ApiResponse;
import com.tiki.profile.dto.request.ProfileCreationRequest;
import com.tiki.profile.dto.request.RegistrationRequest;
import com.tiki.profile.dto.request.UpdateProfileRequest;
import com.tiki.profile.dto.response.ProfileResponse;
import com.tiki.profile.service.ProfileService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileController {

    ProfileService profileService;

    @PostMapping("/register")
    ApiResponse<ProfileResponse> register(@Valid @RequestBody RegistrationRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.register(request))
                .build();
    }

    @GetMapping("/profiles/my-profile")
    ApiResponse<ProfileResponse> getMyProfile() {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.getCurrentProfile())
                .build();
    }
    @PutMapping("/profiles/my-profile")
    ApiResponse<ProfileResponse> updateMyProfile(@RequestBody UpdateProfileRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.updateProfile(request))
                .build();
    }

    @GetMapping("/profiles")
    ApiResponse<List<ProfileResponse>> getAllProfiles() {
        return ApiResponse.<List<ProfileResponse>>builder()
                .result(profileService.getAllProfiles())
                .build();
    }

}
