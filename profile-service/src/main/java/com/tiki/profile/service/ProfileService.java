package com.tiki.profile.service;

import com.tiki.profile.dto.identity.Credential;
import com.tiki.profile.dto.identity.TokenExchangeParam;
import com.tiki.profile.dto.identity.TokenExchangeResponse;
import com.tiki.profile.dto.identity.UserCreationParam;
import com.tiki.profile.dto.request.ProfileCreationRequest;
import com.tiki.profile.dto.request.RegistrationRequest;
import com.tiki.profile.dto.request.UpdateProfileRequest;
import com.tiki.profile.dto.response.ProfileResponse;
import com.tiki.profile.entity.Profile;
import com.tiki.profile.exception.AppException;
import com.tiki.profile.exception.ErrorCode;
import com.tiki.profile.exception.ErrorNormalizer;
import com.tiki.profile.repository.IdentityClient;
import com.tiki.profile.repository.ProfileRepository;
import feign.FeignException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProfileService {

    ProfileRepository profileRepository;
    IdentityClient identityClient;

    @Value("${idp.client-id}")
    @NonFinal
    String clientId;

    @Value("${idp.client-secret}")
    @NonFinal
    String clientSecret;

    ErrorNormalizer errorNormalizer;

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

    public ProfileResponse register(RegistrationRequest request) {
        try {
            TokenExchangeResponse token = identityClient.exchangeToken(TokenExchangeParam.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .scope("openid").build());

            log.info("TokenInfo: {}", token);
            ResponseEntity<?> creationResponse = identityClient.createUser("Bearer " + token.getAccessToken(),
                    UserCreationParam.builder().username(request.username())
                            .firstName(request.firstName())
                            .lastName(request.lastName())
                            .email(request.email())
                            .enabled(true)
                            .emailVerified(false)
                            .credentials(List.of(Credential.builder()
                                    .type("password")
                                    .temporary(false)
                                    .value(request.password()).build())).build());
            String userId = extractUserId(creationResponse);
            log.info("UserId: {}", userId);
            Profile profile = Profile.builder()
                    .userId(userId)
                    .username(request.username())
                    .firstName(request.firstName())
                    .lastName(request.lastName())
                    .email(request.email())
                    .dob(request.dob())
                    .build();
            profileRepository.save(profile);
            return ProfileResponse.from(profile);
        } catch (FeignException exception) {
            throw errorNormalizer.handleKeyCloakException(exception);
        }

//        Profile profile = Profile.builder()
//                .userId(request.userId())
//                .avatar(request.avatar())
//                .dob(request.dob())
//                .city(request.city())
//                .firstName(request.firstName())
//                .lastName(request.lastName())
//                .username(request.username())
//                .email(request.email())
//                .build();
//        return ProfileResponse.from(profileRepository.save(profile))    ;
    }

    private String extractUserId(ResponseEntity<?> response) {
        String location = response.getHeaders().get("Location").getFirst();
        String[] splitedStr = location.split("/");
        return splitedStr[splitedStr.length - 1];
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

    @PreAuthorize("hasRole('ADMIN')")
    public List<ProfileResponse> getAllProfiles() {
        var profiles = profileRepository.findAll();
        return profiles.stream().map(ProfileResponse::from).toList();
    }
}
