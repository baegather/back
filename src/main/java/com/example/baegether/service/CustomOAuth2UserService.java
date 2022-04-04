package com.example.baegether.service;

import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService {

    private final UserService userService;
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> defaultOAuth2UserService = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = defaultOAuth2UserService.loadUser(userRequest);

        //DB에서 userId를 조회해 DB에 없다면 유저 엔티티를 저장합니다.
        if(!userService.isOAuth2UserSaved(oAuth2User)){
            userService.save(oAuth2User);
        }
        return oAuth2User;
    }
}
