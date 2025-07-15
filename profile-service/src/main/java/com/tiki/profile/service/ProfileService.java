package com.tiki.profile.service;

import com.tiki.profile.dto.request.ProfileCreationRequest;
import com.tiki.profile.dto.request.UpdateProfileRequest;
import com.tiki.profile.dto.response.ProfileResponse;
import com.tiki.profile.entity.Profile;
import com.tiki.profile.exception.AppException;
import com.tiki.profile.exception.ErrorCode;
import com.tiki.profile.repository.ProfileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileService {

    ProfileRepository profileRepository;

    public ProfileResponse createProfile(ProfileCreationRequest request) {
        Profile profile = Profile.builder()
                .userId(request.userId())
                .avatar(request.avatar())
                .dob(request.dob())
                .city(request.city())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .build();
        return ProfileResponse.from(profileRepository.save(profile))    ;
    }

    public ProfileResponse getCurrentProfile() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Profile currentProfile = profileRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return ProfileResponse.from(currentProfile);
    }

    public ProfileResponse updateProfile(UpdateProfileRequest request) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
        Profile currentProfile = profileRepository.findByUserId(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        currentProfile.setFirstName(request.firstName());
        currentProfile.setLastName(request.lastName());
        currentProfile.setDob(request.dob());
        currentProfile.setCity(request.city());
        currentProfile.setAvatar(request.avatar());
        return ProfileResponse.from(profileRepository.save(currentProfile))    ;

    }
}
