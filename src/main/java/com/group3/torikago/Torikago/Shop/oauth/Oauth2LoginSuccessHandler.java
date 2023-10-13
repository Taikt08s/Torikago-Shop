package com.group3.torikago.Torikago.Shop.oauth;

import com.group3.torikago.Torikago.Shop.model.AuthenticationProvider;
import com.group3.torikago.Torikago.Shop.model.User;
import com.group3.torikago.Torikago.Shop.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Oauth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private UserService userService;

    public Oauth2LoginSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        CustomOauth2User oAuth2User = (CustomOauth2User) authentication.getPrincipal();
        String email = oAuth2User.getEmail();
        User user = userService.findByEmail(email);
        String name = oAuth2User.getName();
        if (user == null) {
            //register as new user
            userService.createNewUserAfterOauthLogin(email, name, AuthenticationProvider.GOOGLE);
        } else {
            //update existing user
            userService.updateUserAfterOauthLogin(user, name, AuthenticationProvider.GOOGLE);
        }
        setDefaultTargetUrl("/torikago");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
