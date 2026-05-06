package com.backend.medconsult.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.backend.medconsult.entity.auth.User;
import com.backend.medconsult.enums.AuthProvider;
import com.backend.medconsult.repository.UserRepository;
import com.backend.medconsult.security.JwtService;

import java.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {

        OAuth2User oauthUser =
                (OAuth2User) authentication.getPrincipal();

        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");
        String picture = oauthUser.getAttribute("picture");
        String providerId = oauthUser.getAttribute("sub");

        System.out.println("===========================");
        System.out.println("OAuth Email: " + email);
        System.out.println("===========================");

        User user =
                userRepository.findByEmail(email)
                        .orElseGet(() -> {

                    User newUser = new User();

                    newUser.setEmail(email);
                    newUser.setFullName(name);
                    newUser.setProfilePhotoUrl(picture);

                    newUser.setProviderId(providerId);

                    newUser.setAuthProvider(AuthProvider.GOOGLE);

                    newUser.setVerified(true);

                    return userRepository.save(newUser);
                });

        String token =
                jwtService.generateToken(user);

        response.sendRedirect(
                "http://localhost:4200/oauth-success?token="
                        + token
        );
    }
}
