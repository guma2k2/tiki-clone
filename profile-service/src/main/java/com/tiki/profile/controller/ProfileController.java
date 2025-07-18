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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/my-profile")
    ApiResponse<ProfileResponse> getMyProfile() {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.getCurrentProfile())
                .build();
    }
    @PutMapping("/my-profile")
    ApiResponse<ProfileResponse> updateMyProfile(@RequestBody UpdateProfileRequest request) {
        return ApiResponse.<ProfileResponse>builder()
                .result(profileService.updateProfile(request))
                .build();
    }

    @GetMapping("/")
    ApiResponse<List<ProfileResponse>> getAllProfiles() {
        return ApiResponse.<List<ProfileResponse>>builder()
                .result(profileService.getAllProfiles())
                .build();
    }
    @GetMapping("/me")
    public List<String> getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }


}
