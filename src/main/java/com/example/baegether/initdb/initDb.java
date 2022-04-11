package com.example.baegether.initdb;

import com.example.baegether.domain.TimeStamp;
import com.example.baegether.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbTestUserInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbTestUserInit() {
            TimeStamp timeStamp = new TimeStamp(LocalDateTime.now(), null);
            User user1 = new User(1L, "test1Eamil", "test1Nickname", "test1Thumbnail", timeStamp);

            em.persist(user1);

            User user2 = new User(2L, "test2Eamil", "test2Nickname", "test2Thumbnail", timeStamp);

            em.persist(user2);
        }

    }
}
