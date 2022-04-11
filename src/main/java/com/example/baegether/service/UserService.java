package com.example.baegether.service;

import com.example.baegether.domain.TimeStamp;
import com.example.baegether.domain.User;
import com.example.baegether.exceptions.NoSuchDataException;
import com.example.baegether.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;

    //OAuth2User 객체를 받아 이 User의 정보가 DB에 저장되어 있는지 확인하는 메서드
    @Transactional(readOnly = true)
    public boolean isOAuth2UserSaved(OAuth2User oAuth2User){
        Optional<User> findUser = userRepository.findById((Long)oAuth2User.getAttributes().get("id"));
        return findUser.isPresent();
    }

    //oAuth2User을 User로 파싱하고 저장함.
    public Long save(OAuth2User oAuth2User){
        User user = oAuth2UserToUser(oAuth2User);
        userRepository.save(user);

        return user.getId();
    }

    //User을 저장함.
    public Long save(User user){
        userRepository.save(user);
        return user.getId();
    }

    //세션을 기반으로 oAuth2User객체를 불러와 User로 파싱 후 리턴하는 함수
    public User getUserFromOAuth2() throws NoSuchDataException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //유저찾기를 실패하면 principal은 "annoymous User"이 담기고, 값이 있으면 OAuth2User 객체가 담긴다.
        if(principal.getClass().isAssignableFrom(String.class)){
            throw new NoSuchDataException("유저 정보를 불러올 수 없습니다.");
        }
        OAuth2User oAuth2User = (OAuth2User) principal;
        return this.oAuth2UserToUser(oAuth2User);
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
