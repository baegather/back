package com.example.baegether.repository.custom.room;

import com.example.baegether.domain.QRoom;
import com.example.baegether.domain.Room;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomRepositoryCustom{

    private final JPAQueryFactory query;

    @Override
    public Room findRoom(Long postId, Long userId) {
        QRoom room = QRoom.room;

        return query.select(room)
                .from(room)
                .where(room.post.id.eq(postId), room.userId.eq(userId))
                .fetchOne();
    }
}
