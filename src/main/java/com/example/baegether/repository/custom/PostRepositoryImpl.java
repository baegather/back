package com.example.baegether.repository.custom;

import com.example.baegether.domain.Post;
import com.example.baegether.domain.QPost;
import com.example.baegether.domain.QRoom;
import com.example.baegether.domain.Room;
import com.example.baegether.domain.enums.Location;
import com.example.baegether.domain.enums.MenuCategory;
import com.example.baegether.service.search.PostSearch;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;


@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{


    private final JPAQueryFactory query;


    @Override
    public List<Post> findFiltered(PostSearch postSearch) {
        QPost post = QPost.post;
        QRoom room = QRoom.room;

        return query.select(post)
                .from(post)
//                .join(post.room, room)
                .where(
                        locationEq(postSearch.getLocation()),
                        menuCategoryEq(postSearch.getMenuCategory()),
                        keywordEq(postSearch.getKeyword())
                )
                //.orderBy(post.timeStamp.createdTime.desc())
                .fetch();
    }


    private  BooleanExpression keywordEq(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        }
        return QPost.post.title.contains(keyword);
    }

    private BooleanExpression menuCategoryEq(MenuCategory menuCategory) {
        if (menuCategory == null) {
            return null;
        }
        return QPost.post.menuCategory.eq(menuCategory);
    }

    private BooleanExpression locationEq(Location location) {
        if (location == null) {
            return null;
        }
        return QPost.post.location.eq(location);
    }
}
