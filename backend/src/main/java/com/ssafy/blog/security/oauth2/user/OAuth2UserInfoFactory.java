package com.ssafy.blog.security.oauth2.user;

import java.util.Map;

import com.ssafy.blog.exception.OAuth2AuthenticationProcessingException;
import com.ssafy.blog.model.AuthProvider;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(AuthProvider.GOOGLE.toString())) {
            return new GoogleOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(
                    "Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}
