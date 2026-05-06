package com.backend.medconsult.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.enums.AuthProvider;
import com.backend.medconsult.enums.Role;
import com.backend.medconsult.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oauthUser = super.loadUser(request);

        try {

            String email = oauthUser.getAttribute("email");
            String name = oauthUser.getAttribute("name");
            String picture = oauthUser.getAttribute("picture");
            String providerId = oauthUser.getAttribute("sub");

            System.out.println("EMAIL: " + email);

            User user = userRepository.findByEmail(email)
                    .orElse(new User());

            user.setEmail(email);
            user.setFullName(name);
            user.setProfilePhotoUrl(picture);
            user.setAuthProvider(AuthProvider.GOOGLE);
            user.setProviderId(providerId);
            user.setVerified(true);
            user.setActive(true);
            user.setRole(Role.PATIENT);
            user.setLastLoginAt(LocalDateTime.now());
            User saved = userRepository.save(user);

            System.out.println("USER SAVED: " + saved.getEmail());

        } catch (Exception e) {

            System.out.println("OAUTH SAVE FAILED");

            e.printStackTrace();
        }

        return oauthUser;
    }

}
