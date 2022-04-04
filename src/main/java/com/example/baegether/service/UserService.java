package com.example.baegether.service;

import com.example.baegether.domain.TimeStamp;
import com.example.baegether.domain.User;
import com.example.baegether.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    //OAuth2User 객체를 받아 이 User의 정보가 DB에 저장되어 있는지 확인하는 메서드
    public boolean isOAuth2UserSaved(OAuth2User oAuth2User){
        Optional<User> findUser = userRepository.findById((Long)oAuth2User.getAttributes().get("id"));
        return findUser.isPresent();
    }

    public Long save(OAuth2User oAuth2User){
        User user = oAuth2UserToUser(oAuth2User);
        userRepository.save(user);

        return user.getId();
    }

    public Long save(User user){
        userRepository.save(user);
        return user.getId();
    }
    private User oAuth2UserToUser(OAuth2User oAuth2User) {
        Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");
        Map<String, Object> kakaoAccountData = (Map<String, Object>) oAuth2User.getAttributes().get("kakao_account");

        String email = (Boolean)kakaoAccountData.get("has_email")? (String)kakaoAccountData.get("email") : null;

        return new User((Long) oAuth2User.getAttributes().get("id")
                , email
                , (String) properties.get("nickname")
                , (String) properties.get("thumbnail_image")
                , new TimeStamp(LocalDateTime.now(), LocalDateTime.now()));
    }
}
