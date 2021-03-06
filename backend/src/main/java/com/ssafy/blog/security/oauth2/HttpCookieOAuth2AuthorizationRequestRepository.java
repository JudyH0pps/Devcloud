package com.ssafy.blog.security.oauth2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.ssafy.blog.util.CookieUtils;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

/**
 * authorization request를 cookie에 save및 retireve 하기 위해 사용 Spring OAuth2는 기본적으로
 * HttpSessionOAuth2AuthorizationRequestRepository를 사용해서 Authorization Request를
 * 저장함
 * 
 * 우리는 JWT를 사용하므로, Session에 이를 저장할 필요가 없음 따라서 customized된
 * HttpCookieOAuth2AuthorizationRepository를 사용해서 Authorization Request를 Base64
 * encoded cookie에 저장한다
 * 
 * OAuth2 protocol은 CSRF attack를 방지하기 위해 'state' parameter를 사용하도록 권장함 인증이 진행될 때
 * application이 authorization request에 state parameter를 전달하면 OAuth2 provider는 이
 * parameter를 그대로 다시 OAuth2 callback을 통해 전달한다. 이때 application은 initally sent한
 * state parameter와 OAuth2 provider가 callback에 전달한 state parameter를 비교해서 사이트의
 * 위변조를 확인한다. 만약 이 state가 일치하지 않는 경우 authentication request를 거절한다 위와 같은 process를
 * 수행하기 위해서 state변수를 어딘가에 저장해 놓아야 함 따라서 이를 위해 shorted-lived cookie에 state와
 * redirect_uri정보를 저장해 놓음
 */
@Component
public class HttpCookieOAuth2AuthorizationRequestRepository
        implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final int COOKIE_EXPIRE_SECONDS = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
                .map(cookie -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class)).orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
            HttpServletResponse response) {
        if (authorizationRequest == null) {
            CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
            CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
            return;
        }

        CookieUtils.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
                CookieUtils.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);
        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtils.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, COOKIE_EXPIRE_SECONDS);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    }
}
