package com.example.baegether.repository.custom.room;

import com.example.baegether.domain.Room;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepositoryCustom  {

    Room findRoom(Long postId, Long userId);
}
